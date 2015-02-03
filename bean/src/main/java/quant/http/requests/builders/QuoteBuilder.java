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
    private List<String> symbols;

    public QuoteBuilder(TDClientConfig config, String url) {
        URIBuilder uri = new URIBuilder()
            .setScheme("https")
            .setHost(config.get("host"))
            .setPath(url)
            .setParameter("source", config.get("source"));

        symbols = new LinkedList<>();

        this.uri = uri;
    }

    public void addSymbols(String[] symbols) {
        this.symbols.addAll(Arrays.asList(symbols));
    }

    public QuoteBuilder addSymbol(String symbol) {
        this.symbols.add(symbol);
        return this;
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
        String symbols = "";
        Iterator<String> it = this.symbols.iterator();
        while(it.hasNext()) {
            symbols += it.next();
            if(it.hasNext()) {
                symbols += ", ";
            }
        }

        uri.setParameter("symbol",symbols);
        get = new HttpGet(uri.build());
        return this;
    }
}