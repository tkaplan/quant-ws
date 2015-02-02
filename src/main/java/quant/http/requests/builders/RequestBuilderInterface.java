package quant.http.requests.builders;

import org.apache.http.client.methods.HttpRequestBase;

/**
 * Created by dev on 12/31/14.
 */
public interface RequestBuilderInterface {
    public String getParseClassName();

    public HttpRequestBase getRequest();
}
