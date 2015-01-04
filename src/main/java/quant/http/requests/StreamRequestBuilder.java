package quant.http.requests;

import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import quant.http.client.TDClient;
import quant.http.config.TDClientConfig;
import quant.http.dao.StreamServerDao;
import quant.http.requests.builders.RequestBuilderInterface;
import quant.http.requests.builders.SLevel2NasdaqBuilder;
import quant.stream.parser.headers.responses.SLevel2Nasdaq;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dev on 12/24/14.
 */
public class StreamRequestBuilder {

    public StreamRequestBuilder() {

    }

    public SLevel2NasdaqBuilder getSLevel2NasdaqBuilder() {
        return new SLevel2NasdaqBuilder();
    }

}
