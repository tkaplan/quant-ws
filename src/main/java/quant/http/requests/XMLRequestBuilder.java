package quant.http.requests;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import quant.http.client.TDClient;
import quant.http.config.TDClientConfig;
import quant.http.dao.StreamServerDao;
import quant.xml.parser.ResponseParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dev on 12/24/14.
 */
public class XMLRequestBuilder {
    private TDClientConfig config;
    private CloseableHttpClient client;
    private Map<String, String> mapPath;
    private StreamServerDao dao;

    public XMLRequestBuilder(TDClientConfig config, CloseableHttpClient client, StreamServerDao dao) {
        this.client = client;
        this.config = config;
        this.dao = dao;
        mapPath = new HashMap<>();
        mapPath.put("SnapshotQuote", "/apps/100/Quote");
        mapPath.put("SymbolLookup", "/apps/100/SymbolLookup");
        mapPath.put("PriceHistory", "/apps/100/PriceHistory");
        mapPath.put("VolatilityHistory", "/apps/100/VolatilityHistory");
        mapPath.put("OptionChain", "/apps/100/OptionChain");
        mapPath.put("BinaryOptionChain", "/apps/100/BinaryOptionChain");
        mapPath.put("BalancesAndPositions", "/apps/100/BalancesAndPositions");
        mapPath.put("OrderStatus", "/apps/100/OrderStatus");
        mapPath.put("History", "/apps/100/History");
        mapPath.put("QuoteDelayed", "/apps/100/QuoteDelayed");
        mapPath.put("MarketOverview", "/apps/100/MarketOverview");
        mapPath.put("News", "/apps/100/NewsManager");
        mapPath.put("FullStoryNews", "/apps/100/FullStoryNews");
        mapPath.put("QuoteNews", "/apps/100/QuoteNews");
    }

    // We now want to begin processing all of our requests
    public void getSnapshotQuotes(String[] symbols) throws URISyntaxException, IOException {
        // Create uri
        URI uri = new URIBuilder()
            .setScheme("https")
            .setHost(config.get("host"))
            .setPath(mapPath.get("SnapshotQuote"))
            .setParameter("source", config.get("source"))
            .build();
        // Create get request
        HttpGet get = new HttpGet(uri);

        // Execute request
        //ResponseParser.Quotes(client.execute(get));

        // Send it to the ResponseParser


        // Return our hashmap
    }

    public void getSymbolLookup(String matchString) {

    }

    /**
     *
     * @param symbols
     * @param periodtype
     * @param period
     * @param intervaltype
     * @param intervalduration
     * @param startdate
     */
    public void getPriceHistory(
        String[] symbols,
        String periodtype,
        int period,
        String intervaltype,
        int intervalduration,
        String startdate
    ) {

    }

    /**
     *
     * @param symbols
     * @param volatilityhistorytype
     * @param intervaltype
     * @param periodtype
     * @param period
     * @param startdate
     * @param enddate
     * @param daystoexpire
     * @param surfacetypeidentifier
     * @param surfacetypevalue
     */
    public void getVolatilityHistory(
        String[] symbols,
        char volatilityhistorytype,
        String intervaltype,
        String periodtype,
        int period,
        String startdate,
        String enddate,
        int daystoexpire,
        String surfacetypeidentifier,
        int[] surfacetypevalue
    ) {

    }

    public void getOptionChain(String symbol) {

    }

    public void getBinaryOptionChain(
        String symbol
    ) {

    }

    public void getBalancesAndPositions() {

    }

    public void getOrderStatus() {

    }

    public void getHistory(
        int type
    ) {

    }

    public void getQuoteDelayed(String symbol) {

    }

    public void getMarketOverview() {

    }

    public void getNews() {

    }

    public void getFullStoryNews(String guid) {

    }

    public void getQuoteNews(String[] symbols) {

    }
}
