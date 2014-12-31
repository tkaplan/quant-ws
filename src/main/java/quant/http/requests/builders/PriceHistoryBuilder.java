package quant.http.requests.builders;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import quant.http.config.TDClientConfig;
import quant.http.requests.XMLRequestBuilder;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by dev on 12/30/14.
 */
public class PriceHistoryBuilder {
    private URIBuilder uri;
    private String requestValue;
    private String intervalType;
    private String intervalDuration;
    private String periodType;
    private String period;
    private String startDate;
    private String endDate;
    private String extended;

    public PriceHistoryBuilder(TDClientConfig config, String url) {
        URIBuilder uri = new URIBuilder()
            .setScheme("https")
            .setHost(config.get("host"))
            .setPath(url)
            .setParameter("type","SYMBOL")
            .setParameter("source", config.get("source"));

        this.uri = uri;
    }

    public HttpGet build() throws URISyntaxException {
        uri.setParameter("requestvalue", requestValue)
            .setParameter("intervaltype", intervalType)
            .setParameter("intervalduration", intervalDuration)
            .setParameter("periodtype", periodType)
            .setParameter("period", period)
            .setParameter("startdate", startDate)
            .setParameter("enddate", endDate)
            .setParameter("extended", extended);

        return new HttpGet(uri.build());
    }

    public void setExtended(String extended) {
        this.extended = extended;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public void setIntervalDuration(String intervalDuration) {
        this.intervalDuration = intervalDuration;
    }

    public void setIntervalType(String intervalType) {
        this.intervalType = intervalType;
    }

    public void setRequestValue(String requestValue) {
        this.requestValue = requestValue;
    }
}