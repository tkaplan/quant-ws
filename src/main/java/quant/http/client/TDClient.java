package quant.http.client;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.client.HttpAsyncClient;
import quant.http.config.TDClientConfig;
import quant.http.dao.StreamServerDao;
import quant.http.requests.RequestBuilder;
import quant.http.requests.StreamRequestBuilder;
import quant.http.requests.XMLRequestBuilder;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

/**
 * Created by dev on 12/20/14.
 */
@Startup
@Singleton(
    description="Shared async client",
    mappedName="TDClient",
    name="TDClient"
)
public class TDClient {

    private CloseableHttpAsyncClient client;
    private TDClientConfig config;
    private RequestBuilder builder;
    private XMLRequestBuilder xmlRequestBuilder;
    private StreamRequestBuilder streamRequestBuilder;
    private StreamServerDao dao;

    @Resource(name = "DefaultManagedExecutorService")
    ManagedExecutorService executor;

    @PostConstruct
    public void startup() throws IOException, URISyntaxException {
        config = new TDClientConfig();
        builder = new RequestBuilder(config);
        CloseableHttpClient client = HttpClients.createDefault();
        // Authenticate logs us in and grabs
        // our stream info
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


}
