package quant.http.requests;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import quant.http.config.TDClientConfig;
import quant.http.dao.StreamServerDao;
import quant.xml.dao.XMLLoginDao;
import quant.xml.dao.XMLStreamInfoDao;
import quant.xml.factories.ResponseHandlerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by dev on 12/20/14.
 */
public class RequestBuilder {
    private Map<String, String> mapPath;

    private TDClientConfig config;

    public RequestBuilder(TDClientConfig config) {
        this.config = config;
        mapPath = new HashMap<>();
        mapPath.put("login", "/apps/300/LogIn");
        mapPath.put("stockQuote", "/apps/100/Quote");
        mapPath.put("streamInfo", "/apps/100/StreamerInfo");
    }

    public StreamServerDao authenticate(CloseableHttpClient client) throws IOException, URISyntaxException {
        HttpPost loginReq = login();
        HttpResponse loginResponse = client.execute(loginReq);
        XMLLoginDao loginDao = ResponseHandlerFactory.getResponseHandler(new XMLLoginDao(), loginResponse);
        HttpGet streamInfoReq = streamInfo();
        HttpResponse streamInfoResponse = client.execute(streamInfoReq);
        XMLStreamInfoDao streamInfoDao = ResponseHandlerFactory.getResponseHandler(new XMLStreamInfoDao(), streamInfoResponse);
        return new StreamServerDao(config, loginDao.getEntity(), streamInfoDao.getEntity());
    }

    public HttpPost login() throws URISyntaxException, MalformedURLException, UnsupportedEncodingException {
        URI uri = new URIBuilder()
            .setScheme("https")
            .setHost(config.get("host"))
            .setPath(mapPath.get("login"))
            .setParameter("source", config.get("source"))
            .build();

        HttpPost post = new HttpPost(uri);

        // Build data to http
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("version", config.get("version")));
        nvps.add(new BasicNameValuePair("source", config.get("source")));
        nvps.add(new BasicNameValuePair("userid", config.get("userid")));
        nvps.add(new BasicNameValuePair("password", config.get("password")));
        // Attach http data
        post.setEntity(new UrlEncodedFormEntity(nvps));

        return post;
    }

    public HttpGet streamInfo() throws URISyntaxException {
        URI uri = new URIBuilder()
            .setScheme("https")
            .setHost(config.get("host"))
            .setPath(mapPath.get("streamInfo"))
            .setParameter("source", config.get("source"))
            .build();

        return new HttpGet(uri);
    }
}
