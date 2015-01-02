package quant.http.client;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
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

    @Resource
    ManagedExecutorService executor;

    @PostConstruct
    public void startup() throws IOException, URISyntaxException, NoSuchMethodException {
        config = new TDClientConfig();
        builder = new RequestBuilder(config);
        client = HttpClients.createDefault();
        ResponseParser.init();
//        Authenticate logs us in and grabs
//        our stream info
        dao = builder.authenticate(client);
        this.xmlRequestBuilder = new XMLRequestBuilder(config, client, dao);
        this.streamRequestBuilder = new StreamRequestBuilder(config, client, dao);
    }

    public XMLRequestBuilder XMLRequestBuilder() {
        return xmlRequestBuilder;
    }

    public StreamRequestBuilder StreamRequestBuilder() {
        return streamRequestBuilder;
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
