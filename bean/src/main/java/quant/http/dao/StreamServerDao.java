package quant.http.dao;


import org.apache.http.Consts;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import quant.http.config.TDClientConfig;
import quant.http.entities.StreamServerEntity;
import quant.xml.entities.XMLLoginEntity;
import quant.xml.entities.XMLStreamInfoEntity;

import javax.inject.Inject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    public StreamServerDao (TDClientConfig config, XMLLoginEntity loginEntity, XMLStreamInfoEntity streamInfoEntity) throws UnsupportedEncodingException, UnknownHostException {
        this.config = config;
        this.xmlLoginEntity = loginEntity;
        this.xmlStreamInfoEntity = streamInfoEntity;
        entity = new StreamServerEntity();
        setPostEntity(loginEntity, streamInfoEntity);
        nvpsInit();
        newStream();
    }

    public void setPostEntity(XMLLoginEntity loginEntity, XMLStreamInfoEntity streamInfoEntity) throws UnknownHostException {
        entity.setAppId(streamInfoEntity.getAppId());
        entity.setAccountId(loginEntity.getAccountId());
        entity.setAcl(streamInfoEntity.getAcl());
        entity.setAccesslevel(streamInfoEntity.getAccesslevel());
        entity.setAuthorized(streamInfoEntity.getAuthorized());
        entity.setCompany(loginEntity.getCompany());
        entity.setSegment(loginEntity.getSegment());
        entity.setCddomain(streamInfoEntity.getCdDomainId());

        // Get ip address
        entity.setStreamUrl(InetAddress.
            getByName(streamInfoEntity.
                getStreamerUrl()).
            getHostAddress());
        //entity.setStreamUrl(streamInfoEntity.getStreamerUrl());
        entity.setTimestamp(streamInfoEntity.getTimestamp());
        entity.setToken(streamInfoEntity.getToken());
        entity.setUsergroup(streamInfoEntity.getUsergroup());
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
        streamRequest = "|S=QUOTE&C=SUBS&P=DELL&T=0";
        scv =  "|control=false" + "|source=" + config.get("source") + "|version=2.3";
    }

    private void markOld () throws UnsupportedEncodingException {
        streamRequest = "";
        scv =  "|control=true" + "|source=" + config.get("source") + "|version=2.3";
    }

    public void setStreamRequest (String request) {
        streamRequest = "|";
        streamRequest += request;
    }

    public HttpGet getStreamRequest() throws URISyntaxException, UnsupportedEncodingException {
        RequestConfig requestConfig = RequestConfig.custom()
            .setExpectContinueEnabled(true)
            .setDecompressionEnabled(true)
            .setRedirectsEnabled(false)
            .build();

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
        request = "http://" + entity.getStreamUrl() + "/" + request;
        HttpGet get = new HttpGet(request);
        get.setConfig(requestConfig);
        markOld();

        return get;
    }
}
