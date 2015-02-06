package quant.http.requests.builders;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import quant.http.config.TDClientConfig;

import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by dev on 12/30/14.
 */
public class QuoteBuilder implements RequestBuilderInterface {
    private URIBuilder uri;
    private HttpGet get;
    private String symbols;

    public QuoteBuilder(TDClientConfig config, String url) {
        URIBuilder uri = new URIBuilder()
            .setScheme("https")
            .setHost(config.get("host"))
            .setPath(url)
            .setParameter("source", config.get("source"));

        this.uri = uri;
    }

    public void setSymbols(String symbols) {
        this.symbols = symbols;
    }

    @Override
    public String getParseClassName() {
        return "Quotes";
    }

    @Override
    public HttpRequestBase getRequest() {
        return get;
    }

    public QuoteBuilder build() throws URISyntaxException {
        uri.setParameter("symbol",symbols);
        get = new HttpGet(uri.build());
        return this;
    }
}