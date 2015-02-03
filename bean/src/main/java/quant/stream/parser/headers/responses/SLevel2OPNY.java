package quant.stream.parser.headers.responses;

import quant.stream.parser.annotations.Column;
import quant.stream.parser.annotations.RepeatableColumns;
import quant.stream.parser.headers.StreamingResponse;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Stack;

/**
 * Created by dev on 12/13/14.
 */
@RepeatableColumns(
    value={
        @Column(index=0,field="symbol",type=String.class),
        @Column(index=1,field="time",type=Integer.class),
        @Column(index=2,field="data",type=ByteBuffer.class)
    }
)
public class SLevel2OPNY extends StreamingResponse {
    private static final Short[] sids = {(short)81,(short)84};
    public static Map<Object, Object> extract(Map<Object, Object> result) throws UnsupportedEncodingException {
        ByteBuffer data = (ByteBuffer)result.get("data");
        String results = new String(data.array(),"UTF-8");
        results = results.trim();
        String[] dataArray = results.split(";");
        Stack<BidData> bids = new Stack<>();
        Stack<AskData> asks = new Stack<>();
        // Number of repeat bids
        int index = 0;
        int bidCount = Integer.parseInt(dataArray[index++]);
        int totalBids = Integer.parseInt(dataArray[index++]);
        int bidLimit = bidCount*3 + index;
        while(index < bidLimit) {
            BidData bid = new BidData();
            bid.setBidMMID(dataArray[index++]);
            bid.setBidPrice(Double.parseDouble(dataArray[index++]));
            bid.setBidSize(Integer.parseInt(dataArray[index++]));
            bids.push(bid);
        }
        int askCount = Integer.parseInt(dataArray[index++]);
        int askTotal = Integer.parseInt(dataArray[index++]);
        int askLimit = askCount*3 + index;
        while(index < askLimit) {
            AskData ask = new AskData();
            ask.setAskMMID(dataArray[index++]);
            ask.setAskPrice(Double.parseDouble(dataArray[index++]));
            ask.setAskSize(Integer.parseInt(dataArray[index++]));
            asks.push(ask);
        }
        result.put("bids", bids);
        result.put("asks", asks);
        return result;
    }

    public static class AskData {
        private String askMMID;
        private double askPrice;
        private int askSize;

        public AskData() {

        }

        public String getAskMMID() {
            return askMMID;
        }

        public void setAskMMID(String askMMID) {
            this.askMMID = askMMID;
        }

        public double getAskPrice() {
            return askPrice;
        }

        public void setAskPrice(double askPrice) {
            this.askPrice = askPrice;
        }

        public int getAskSize() {
            return askSize;
        }

        public void setAskSize(int askSize) {
            this.askSize = askSize;
        }
    }

    public static class BidData {
        private String bidMMID;
        private double bidPrice;
        private int bidSize;

        public BidData() {

        }

        public String getBidMMID() {
            return bidMMID;
        }

        public void setBidMMID(String bidMMID) {
            this.bidMMID = bidMMID;
        }

        public double getBidPrice() {
            return bidPrice;
        }

        public void setBidPrice(double bidPrice) {
            this.bidPrice = bidPrice;
        }

        public int getBidSize() {
            return bidSize;
        }

        public void setBidSize(int bidSize) {
            this.bidSize = bidSize;
        }
    }
}
