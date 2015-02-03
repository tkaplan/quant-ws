package quant.stream.parser.headers.responses;

import quant.stream.parser.annotations.Column;
import quant.stream.parser.annotations.RepeatableColumns;
import quant.stream.parser.headers.StreamingResponse;

import javax.annotation.Resource;
import javax.annotation.concurrent.NotThreadSafe;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by dev on 12/13/14.
 */
@RepeatableColumns(
        value={
                @Column(index=0,field="symbol",type=String.class),
                @Column(index=1,field="time",type=Integer.class),
                @Column(index=2,field="bidBook",type=String.class),
                @Column(index=3,field="askBook",type=String.class),
                @Column(index=4,field="NO2Data",type=String.class)
        }
)
public class SLevel2Nasdaq extends StreamingResponse{
        private static final Short sid = (short)87;
        public static Map<Object, Object> extract(Map<Object, Object> result) throws UnsupportedEncodingException, InterruptedException, NamingException, ExecutionException {
                ManagedExecutorService executor = InitialContext.doLookup("java:comp/DefaultManagedExecutorService");
                Stack<BookData> askBook = new Stack<>();
                Stack<BookData> bidBook = new Stack<>();
                // Parallelize our parsing
                ExtractData bidBookData = new ExtractData((String)result.get("bidBook"), askBook);
                ExtractData askBookData = new ExtractData((String)result.get("askBook"), bidBook);
                Future bid = executor.submit(bidBookData);
                Future ask = executor.submit(askBookData);
                // Wait for both bid and ask to finish
                bid.get();
                ask.get();
                // Add to our result
                result.put("bidBook", bidBook);
                result.put("askBook", askBook);
                return result;
        }

        public static class BookData {
                private double price;
                private int aggregateSize;
                private Stack<MPID> MPIDs;
                public BookData() {
                        MPIDs = new Stack<>();
                }

                public double getPrice() {
                        return price;
                }

                public void setPrice(double price) {
                        this.price = price;
                }

                public int getAggregateSize() {
                        return aggregateSize;
                }

                public void setAggregateSize(int aggregateSize) {
                        this.aggregateSize = aggregateSize;
                }

                public Stack<MPID> getMPIDs() {
                        return MPIDs;
                }

                public void setMPIDs(Stack<MPID> MPIDs) {
                        this.MPIDs = MPIDs;
                }

                public void addMPID(MPID mpid) {
                        this.MPIDs.push(mpid);
                }
        }

        public static class MPID {
                private String mpid;
                private int size;
                private long time;
                public MPID() {

                }

                public String getMpid() {
                        return mpid;
                }

                public void setMpid(String mpid) {
                        this.mpid = mpid;
                }

                public int getSize() {
                        return size;
                }

                public void setSize(int size) {
                        this.size = size;
                }

                public long getTime() {
                        return time;
                }

                public void setTime(long time) {
                        this.time = time;
                }
        }

        @NotThreadSafe
        private static class ExtractData implements Runnable {
                volatile String data;
                volatile Stack<BookData> book;
                public ExtractData(String data, Stack<BookData> book) {
                        this.book = book;
                        this.data = data;
                }

                @Override
                public void run() {
                        // Prep our data
                        String[] dataArray = data.split(";");
                        int index = 0;
                        int totalRowCount = Integer.parseInt(dataArray[index++]);
                        int levelCount = Integer.parseInt(dataArray[index++]);
                        for(int i = 0; i < levelCount; i ++) {
                                BookData bookData = new BookData();
                                bookData.setPrice(Double.parseDouble(dataArray[index++]));
                                bookData.setAggregateSize(Integer.parseInt(dataArray[index++]));
                                int mpidCount = Integer.parseInt(dataArray[index++]);
                                for(int j = 0; j < mpidCount; j++ ) {
                                        MPID mpid = new MPID();
                                        mpid.setMpid(dataArray[index++]);
                                        mpid.setSize(Integer.parseInt(dataArray[index++]));
                                        mpid.setTime(Long.parseLong(dataArray[index++]));
                                        bookData.addMPID(mpid);
                                }
                                book.push(bookData);
                        }
                }
        }
}
