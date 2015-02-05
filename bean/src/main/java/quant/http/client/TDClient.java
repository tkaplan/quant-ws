package quant.http.client;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import quant.http.config.TDClientConfig;
import quant.http.dao.StreamServerDao;
import quant.http.requests.RequestBuilder;
import quant.http.requests.StreamRequestBuilder;
import quant.http.requests.XMLRequestBuilder;
import quant.http.requests.builders.RequestBuilderInterface;
import quant.stream.iostream.MapObserver;
import quant.stream.manager.StatusHolder;
import quant.stream.manager.StreamManager;
import quant.stream.parser.headers.HeaderManager;
import quant.xml.parser.ResponseParser;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by dev on 12/20/14.
 */
@Startup
@Singleton
public class TDClient {

    private CloseableHttpClient client;
    private TDClientConfig config;
    private RequestBuilder builder;
    private XMLRequestBuilder xmlRequestBuilder;
    private StreamRequestBuilder streamRequestBuilder;
    private StreamServerDao dao;
    private StreamManager streamManager;

    @Resource
    ManagedExecutorService executor;

    @PostConstruct
    public void startup() {
        try {
            config = new TDClientConfig();
            builder = new RequestBuilder(config);
            client = HttpClients.createDefault();
            ResponseParser.init();
            HeaderManager.init();
            // Authenticate logs us in and grabs
            // our stream info
            this.streamManager = new StreamManager(builder.authenticate(client), client, executor);
            this.xmlRequestBuilder = new XMLRequestBuilder(config);
        } catch(Exception e) {

        }
    }

    public XMLRequestBuilder XMLRequestBuilder() {
        return xmlRequestBuilder;
    }

    public StreamRequestBuilder StreamRequestBuilder() {
        return streamRequestBuilder;
    }

    public void startStreaming() throws Exception {
        streamManager.start();
    }

    public StatusHolder updateStreaming(String request) throws Exception {
        return streamManager.update(request);
    }

    public void stopStream() throws Exception {
        streamManager.stop();
    }

    public void clearStream() throws Exception {
        streamManager.clear();
    }

    public void registerMapObserver(MapObserver mo) {
        streamManager.registerMapObserver(mo);
    }

    public void unregisterMapObserver(MapObserver mo) {
        streamManager.unregisterMapObserver(mo);
    }

    public void clearMapObserversForClass(Class clazz) {
        streamManager.clearMapObserversForClass(clazz);
    }

    public void clearAllMapObservers() {
        streamManager.clearAllMapObservers();
    }

    // Refresh our stream info
    public synchronized void setStreamDao(StreamServerDao dao) {
        this.dao = dao;
    }

    public Future execute(RequestBuilderInterface request) {
        Callable task = new Callable<Map>() {
            @Override
            public Map call() throws Exception {
                Map responseMap = null;
                HttpResponse response = client.execute(request.getRequest());
                responseMap = ResponseParser.parse(response.getEntity().getContent(), request.getParseClassName());
                return responseMap;
            }
        };
        return executor.submit(task);
    }
}
