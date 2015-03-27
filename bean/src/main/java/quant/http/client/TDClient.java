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
import quant.stream.parser.headers.responses.SLevel1Equity;
import quant.stream.parser.headers.responses.SLevel1Option;
import quant.stream.parser.headers.responses.SLevel2Nasdaq;
import quant.stream.parser.headers.responses.SLevel2OPNY;
import quant.xml.parser.ResponseParser;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by dev on 12/20/14.
 */
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
public class TDClient {

    private CloseableHttpClient client;
    private TDClientConfig config;
    private RequestBuilder builder;
    private XMLRequestBuilder xmlRequestBuilder;
    private StreamRequestBuilder streamRequestBuilder;
    private StreamServerDao dao;
    private StreamManager streamManager;
    private Map<String, Class> parseIdMap;
    private Map<String, Class> dtoMap;
    private Map<String, String> tIdMap;
    private boolean isStreaming = false;

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

        } finally {
            parseIdMap = new HashMap();
            parseIdMap.put("QUOTE", SLevel1Equity.class);
            parseIdMap.put("OPTION", SLevel1Option.class);
            parseIdMap.put("OPRA", SLevel2OPNY.class);
            parseIdMap.put("NYSE_BOOK", SLevel2OPNY.class);
            parseIdMap.put("TOTAL_VIEW", SLevel2Nasdaq.class);

            tIdMap = new HashMap<>();

            // Level 1 Quote
            tIdMap.put("QUOTE", "0+1+2+3+4" +
                "+5+6+7+8+9+10+11+12+13+14+" +
                "15+16+17+18+22+23+24+25+26+" +
                "27+28+29+30+31+32+33+34+37+38+" +
                "39+40");

            // Level 1 Option
            tIdMap.put("OPTION", "0+1+2+3+4+5+6+7+8+9" +
                "+10+11+12+13+14+15+16+17+19+20+21" +
                "+22+23+24+25+26+27+28+29+31+32+33+34+35+36");

            // Level 2 Option
            tIdMap.put("OPRA", "0+1+2");

            // Level 2 NYSE_BOOK
            tIdMap.put("NYSE_BOOK", "0+1+2");

            // Level 2 NASDAQ
            tIdMap.put("TOTAL_VIEW", "0+1+2+3");
        }
    }

    public String getT(String key) {
        return tIdMap.get(key);
    }

    public Class getParseIdForString(String key) {
        return parseIdMap.get(key);
    }

    public XMLRequestBuilder XMLRequestBuilder() {
        return xmlRequestBuilder;
    }

    public StreamRequestBuilder StreamRequestBuilder() {
        return streamRequestBuilder;
    }

    public synchronized void startStreaming() throws Exception {
        if(!isStreaming)
            streamManager.start();
        isStreaming = true;
    }

    public StatusHolder updateStreaming(String request) throws Exception {
        return streamManager.update(request);
    }

    public synchronized void stopStream() throws Exception {
        if(isStreaming)
            streamManager.stop();
        isStreaming = false;
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

    public boolean isStreaming() {
        return isStreaming;
    }
}
