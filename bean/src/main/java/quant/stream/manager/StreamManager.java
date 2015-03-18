package quant.stream.manager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import quant.http.dao.StreamServerDao;
import quant.stream.iostream.MapObserver;
import quant.stream.iostream.MapStream;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by dev on 1/2/15.
 */
public class StreamManager {

    private volatile StreamServerDao dao;
    private volatile MapStream mapStream;
    private volatile HttpClient client;
    private volatile String cookie = null;
    private volatile HttpRequestBase connection = null;

    @Resource
    private volatile ManagedExecutorService executor;

    public StreamManager(StreamServerDao dao, HttpClient client, ManagedExecutorService executor) {
        this.dao = dao;
        this.client = client;
        this.executor = executor;
    }

    // Starts the thread
    public synchronized void start() throws Exception {
        dao.newStream();
        connection = dao.getStreamRequest();
        HttpResponse response = client.execute(connection);
        cookie = response.getFirstHeader("Set-Cookie").getValue().split(";")[0];
        mapStream = new MapStream(response.getEntity().getContent());

        executor.submit(
            () -> {
                try {
                    mapStream.start();
                } catch (Exception e) {
                    if(!(e instanceof java.io.EOFException)
                        &&
                        !(e instanceof java.net.SocketException)) {
                        e.printStackTrace();
                    }
                    System.out.println("Stream stopped successfully!");
                }
                return;
            }
        );
    }

    // Effectively stops our request
    public synchronized void stop() throws Exception {
        if(connection == null)
            throw new Exception("Please start the stream before operating.");
        connection.releaseConnection();
        connection = null;
    }

    // Updates our stream request (true if successful, false if not)
    public synchronized StatusHolder update(String request) throws Exception {
        int count = 3;
        StatusHolder status = null;
        while(count-- > 0 || status == null) {
            // Should technically block
            status = updateRequest(request);
            if(status.get() == Status.OK)
                break;
        }
        return status;
    }

    // This method should block for a max of 10 seconds until we have
    // status of our request
    private StatusHolder updateRequest(String request) throws Exception {
        dao.setStreamRequest(request);
        HttpRequestBase httpRequest = dao.getStreamRequest();
        httpRequest.setHeader("Cookie", cookie);
        System.out.println("Updating $$$$$$$$$$$$");
        System.out.println("Updating $$$$$$$$$$$$");
        System.out.println(httpRequest.getURI());
        System.out.println("Updating $$$$$$$$$$$$");
        System.out.println("Updating $$$$$$$$$$$$");
        HttpResponse response = client.execute(httpRequest);
        httpRequest.releaseConnection();
        if(response.getStatusLine().getStatusCode() < 300)
            return new StatusHolder(Status.OK);
        return new StatusHolder(Status.ERROR);
    }

    public void clear() throws Exception {
        stop();
        start();
    }

    public void registerMapObserver(MapObserver mo) {
        mapStream.registerMapObserver(mo);
    }

    public void unregisterMapObserver(MapObserver mo) {
        mapStream.unregisterMapObserver(mo);
    }

    public void clearMapObserversForClass(Class clazz) {
        mapStream.clearMapObserversForClass(clazz);
    }

    public void clearAllMapObservers() {
        mapStream.clearAllMapObservers();
    }
}
