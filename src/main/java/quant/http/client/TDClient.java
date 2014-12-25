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

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
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
    private StreamServerDao dao;
    private TDClientConfig config;
    private RequestBuilder builder;

    @PostConstruct
    public void startup() throws IOException, URISyntaxException {
        config = new TDClientConfig();
        this.builder = new RequestBuilder(config);
        CloseableHttpClient client = HttpClients.createDefault();
        // Authenticate logs us in and grabs
        // our stream info
        this.dao = builder.authenticate(client);
    }

    /**
     * This bean will:
     *  1) authenticate
     *  2) make requests on our behalf
     *  3) log
     *  4) return nio streams
     *  5) manage event listeners
     *  6) parse
     *  7) persist via hibernate.
     */

    // Refresh our stream info
    public synchronized void setStreamDao(StreamServerDao dao) {
        this.dao = dao;
    }

    // We now want to begin processing all of our requests
    public void getStockQuote() {

    }
}
