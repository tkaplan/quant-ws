package quant.http.requests.builders;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import quant.http.config.TDClientConfig;

import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by dev on 12/30/14.
 */
public class QuoteBuilder {
    private URIBuilder uri;
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

    public void addSymbol(String symbol) {
        this.symbols.add(symbol);
    }

    public HttpGet build() throws URISyntaxException {
        String symbols = "";
        Iterator<String> it = this.symbols.iterator();
        while(it.hasNext()) {
            symbols += it.next();
            if(it.hasNext()) {
                symbols += ",";
            }
        }

        uri.setParameter("symbol",symbols);
        return new HttpGet(uri.build());
    }
}