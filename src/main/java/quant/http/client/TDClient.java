package quant.http.client;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.xml.sax.SAXException;
import quant.http.config.TDClientConfig;
import quant.http.dao.StreamServerDao;
import quant.http.requests.RequestBuilder;
import quant.http.requests.StreamRequestBuilder;
import quant.http.requests.XMLRequestBuilder;
import quant.http.requests.builders.RequestBuilderInterface;
import quant.stream.manager.StreamManager;
import quant.stream.parser.headers.HeaderManager;
import quant.xml.parser.ResponseParser;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by dev on 12/20/14.
 */
@Startup
@Singleton(
    mappedName="TDClient",
    name="TDClient"
)
public class
    TDClient {

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
    public void startup() throws Exception {
        config = new TDClientConfig();
        builder = new RequestBuilder(config);
        client = HttpClients.createDefault();
        ResponseParser.init();
        HeaderManager.init();
        // Authenticate logs us in and grabs
        // our stream info
        this.streamManager = new StreamManager(builder.authenticate(client), client, executor);
        this.xmlRequestBuilder = new XMLRequestBuilder(config);
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

    public void updateStreaming(String request) throws Exception {
        streamManager.update(request);
    }

    public void stopStream() throws Exception {
        streamManager.stop();
    }

    public void clearStream() throws Exception {
        streamManager.clear();
    }

    // Returns a stream of only our relevant maps
    public void getMapStream() {

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
