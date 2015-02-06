package quant.http.requests.builders;

import org.apache.http.client.methods.HttpRequestBase;

import java.net.URISyntaxException;

/**
 * Created by dev on 12/31/14.
 */
public interface RequestBuilderInterface {
    public String getParseClassName();

    public HttpRequestBase getRequest();

    public RequestBuilderInterface build() throws URISyntaxException;
}
