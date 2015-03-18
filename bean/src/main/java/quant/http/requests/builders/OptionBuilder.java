package quant.http.requests.builders;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import quant.http.config.TDClientConfig;

import java.net.URISyntaxException;

/**
 * Created by dev on 2/18/15.
 */
public class OptionBuilder implements RequestBuilderInterface {
    private HttpGet get;
    private URIBuilder uri;
    private String symbol;
    private String type;

    public OptionBuilder(TDClientConfig config, String url) {
        URIBuilder uri = new URIBuilder()
            .setScheme("https")
            .setHost(config.get("host"))
            .setPath(url)
            .setParameter("source", config.get("source"));

        this.uri = uri;
    }

    @Override
    public String getParseClassName() {
        return "OptionChain";
    }

    @Override
    public HttpRequestBase getRequest() {
        return get;
    }

    @Override
    public RequestBuilderInterface build() throws URISyntaxException {
        uri.setParameter("symbol",symbol);
        uri.setParameter("type",type);
        get = new HttpGet(uri.build());
        return this;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setType(String type) {
        this.type = type;
    }
}
