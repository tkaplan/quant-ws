package quant.http.dao;


import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import quant.http.config.TDClientConfig;
import quant.http.entities.StreamServerEntity;
import quant.xml.entities.XMLLoginEntity;
import quant.xml.entities.XMLStreamInfoEntity;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev on 12/23/14.
 */
public class StreamServerDao {
    private StreamServerEntity entity;
    private XMLLoginEntity xmlLoginEntity;
    private XMLStreamInfoEntity xmlStreamInfoEntity;
    private List<NameValuePair> nvps;
    private String streamRequest;
    private String scv;
    private TDClientConfig config;

    public StreamServerDao (TDClientConfig config) {
        entity = new StreamServerEntity();
        this.config = config;
    }

    public StreamServerDao (TDClientConfig config, XMLLoginEntity loginEntity, XMLStreamInfoEntity streamInfoEntity) {
        this.config = config;
        this.xmlLoginEntity = loginEntity;
        this.xmlStreamInfoEntity = streamInfoEntity;
        entity = new StreamServerEntity();
        setPostEntity(loginEntity, streamInfoEntity);
        streamRequest = "";
        nvpsInit();
        try {
            newStream();
        } catch (UnsupportedEncodingException e) {
            System.out.println("Couldn't initialize scv.");
        }
    }

    public void setPostEntity(XMLLoginEntity loginEntity, XMLStreamInfoEntity streamInfoEntity) {
        entity.setAppId(streamInfoEntity.getAppId());
        entity.setAccountId(loginEntity.getAccountId());
        entity.setAcl(streamInfoEntity.getAcl());
        entity.setAccesslevel(streamInfoEntity.getAccesslevel());
        entity.setAuthorized(streamInfoEntity.getAuthorized());
        entity.setCompany(loginEntity.getCompany());
        entity.setSegment(loginEntity.getSegment());
        entity.setCddomain(streamInfoEntity.getCdDomainId());
        entity.setStreamUrl(streamInfoEntity.getStreamerUrl());
        entity.setTimestamp(streamInfoEntity.getTimestamp());
        entity.setToken(streamInfoEntity.getToken());
        entity.setUsergroup(streamInfoEntity.getUsergroup());
    }

    public StreamServerEntity getEntity () {
        return entity;
    }

    public void setPostEntity() {

    }

    private void nvpsInit () {
        nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("!U", entity.getAccountId()));
        nvps.add(new BasicNameValuePair("W", entity.getToken()));
        nvps.add(new BasicNameValuePair("A=userid", entity.getAccountId()));
        nvps.add(new BasicNameValuePair("token", entity.getToken()));
        nvps.add(new BasicNameValuePair("company", entity.getCompany()));
        nvps.add(new BasicNameValuePair("segment", entity.getSegment()));
        nvps.add(new BasicNameValuePair("cddomain", entity.getCddomain()));
        nvps.add(new BasicNameValuePair("usergroup", entity.getUsergroup()));
        nvps.add(new BasicNameValuePair("accesslevel", entity.getAccesslevel()));
        nvps.add(new BasicNameValuePair("authorized", entity.getAuthorized()));
        nvps.add(new BasicNameValuePair("acl", entity.getAcl()));
        nvps.add(new BasicNameValuePair("timestamp", entity.getTimestamp()));
        nvps.add(new BasicNameValuePair("appid", entity.getAppId()));
    }

    public void newStream () throws UnsupportedEncodingException {
        String source = config.get("source");
        String control = "false";
        String version= config.get("version");
        scv = "|source=" + source + "|control=" + control;
    }

    private void markOld () throws UnsupportedEncodingException {
        String source = config.get("source");
        String control = "true";
        String version= config.get("version");
        streamRequest = "";
        scv = "|source=" + source + "|control=" + control;
    }

    public void setStreamRequest (String request) {
        streamRequest = request;
    }

    public HttpGet getURLStreamRequest() throws URISyntaxException, UnsupportedEncodingException {

        String nvpsStr = "";

        Object myArray[] = nvps.toArray();
        for(int i = 0; i < myArray.length; i ++) {
            nvpsStr += myArray[i].toString();
            if(i < myArray.length - 1) {
                nvpsStr += "&";
            }
        }

        String request = nvpsStr + scv + streamRequest;
        request = URLEncoder.encode(request, "UTF-8");
        request = "https://" + entity.getStreamUrl() + "/" + request;
        markOld();

        return new HttpGet(request);
    }
}
