package quant.http.requests;

import org.apache.http.impl.client.CloseableHttpClient;
import quant.http.client.TDClient;
import quant.http.config.TDClientConfig;
import quant.http.dao.StreamServerDao;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dev on 12/24/14.
 */
public class StreamRequestBuilder {
    private TDClientConfig config;
    private TDClient client;
    private Map<String, String> mapPath;

    public StreamRequestBuilder(TDClientConfig config, CloseableHttpClient client, StreamServerDao dao) {
        this.config = config;
        mapPath = new HashMap<>();
        mapPath.put("login", "/apps/300/LogIn");
        mapPath.put("stockQuote", "/apps/100/Quote");
        mapPath.put("streamInfo", "/apps/100/StreamerInfo");
    }
}
