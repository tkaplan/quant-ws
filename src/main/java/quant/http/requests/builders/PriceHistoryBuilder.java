package quant.http.requests.builders;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import quant.http.config.TDClientConfig;
import quant.http.requests.XMLRequestBuilder;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by dev on 12/30/14.
 */
public class PriceHistoryBuilder implements RequestBuilderInterface {
    private URIBuilder uri;
    private String requestValue;
    private String intervalType;
    private String intervalDuration;
    private String periodType;
    private String period;
    private String startDate;
    private String endDate;
    private String extended;
    private HttpGet get;

    public PriceHistoryBuilder(TDClientConfig config, String url) {
        URIBuilder uri = new URIBuilder()
            .setScheme("https")
            .setHost(config.get("host"))
            .setPath(url)
            .addParameter("requestidentifiertype", "SYMBOL")
            .addParameter("source", config.get("source"));

        this.uri = uri;
    }

    @Override
    public String getParseClassName() {
        return "PriceHistory";
    }

    @Override
    public HttpRequestBase getRequest() {
        return get;
    }

    public PriceHistoryBuilder build() throws URISyntaxException {
        uri.setParameter("requestvalue", requestValue)
            .setParameter("intervaltype", intervalType)
            .setParameter("intervalduration", intervalDuration);

        if(periodType != null)
            uri.setParameter("periodtype", periodType);
        if(period != null)
            uri.setParameter("period", period);
        if(startDate != null)
            uri.setParameter("startdate", startDate);
        if(endDate != null)
            uri.setParameter("enddate", endDate);
        if(extended != null)
            uri.setParameter("extended", extended);

        get = new HttpGet(uri.build());
        RequestConfig requestConfig = RequestConfig.custom()
            .setExpectContinueEnabled(true)
            .setDecompressionEnabled(true)
            .build();

        get.setConfig(requestConfig);
        return this;
    }

    public PriceHistoryBuilder setExtended(String extended) {
        this.extended = extended;
        return this;
    }

    public PriceHistoryBuilder setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public PriceHistoryBuilder setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public PriceHistoryBuilder setPeriod(String period) {
        this.period = period;
        return this;
    }

    public PriceHistoryBuilder setPeriodType(String periodType) {
        this.periodType = periodType;
        return this;
    }

    public PriceHistoryBuilder setIntervalDuration(String intervalDuration) {
        this.intervalDuration = intervalDuration;
        return this;
    }

    public PriceHistoryBuilder setIntervalType(String intervalType) {
        this.intervalType = intervalType;
        return this;
    }

    public PriceHistoryBuilder setRequestValue(String requestValue) {
        this.requestValue = requestValue;
        return this;
    }
}