package quant.http.requests;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import quant.http.config.TDClientConfig;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dev on 12/20/14.
 */
public abstract class RequestBuilder {
    private static Map<String, String> mapPath;

    @Inject
    private static TDClientConfig config;

    public static void init() {
        mapPath = new HashMap<>();
        mapPath.put("login", "/apps/300/LogIn");
        mapPath.put("stockQuote", "/apps/100/Quote");
        mapPath.put("streamInfo", "/apps/100/StreamerInfo");
    }

    public static HttpPost login() throws URISyntaxException, MalformedURLException, UnsupportedEncodingException {
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

    public static HttpGet streamInfo() throws URISyntaxException {
        URI uri = new URIBuilder()
            .setScheme("https")
            .setHost(config.get("host"))
            .setPath(mapPath.get("streamInfo"))
            .setParameter("source", config.get("source"))
            .build();

        return new HttpGet(uri);
    }
}
