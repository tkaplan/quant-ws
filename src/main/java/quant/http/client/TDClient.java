package quant.http.client;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.client.HttpAsyncClient;
import quant.http.requests.RequestBuilder;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

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

    @PostConstruct
    public void startup() {
        CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
        // Initiate our request paths
        RequestBuilder.init();
    }

    /**
     *
     */
}
