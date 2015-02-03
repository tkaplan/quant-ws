package quant.xml.entities;

import org.apache.http.HttpResponse;

/**
 * Created by dev on 12/23/14.
 */
public class XMLStreamInfoEntity extends XMLEntity {
    private String streamerUrl;
    private String token;
    private String timestamp;
    private String cdDomainId;
    private String usergroup;
    private String accesslevel;
    private String acl;
    private String appId;
    private String authorized;
    private HttpResponse response;

    public XMLStreamInfoEntity () {

    }

    public String getAuthorized() {
        return authorized;
    }

    public void setAuthorized(String authorized) {
        this.authorized = authorized;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAcl() {
        return acl;
    }

    public void setAcl(String acl) {
        this.acl = acl;
    }

    public String getAccesslevel() {
        return accesslevel;
    }

    public void setAccesslevel(String accesslevel) {
        this.accesslevel = accesslevel;
    }

    public String getUsergroup() {
        return usergroup;
    }

    public void setUsergroup(String usergroup) {
        this.usergroup = usergroup;
    }

    public String getCdDomainId() {
        return cdDomainId;
    }

    public void setCdDomainId(String cdDomainId) {
        this.cdDomainId = cdDomainId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStreamerUrl() {
        return streamerUrl;
    }

    public void setStreamerUrl(String streamerUrl) {
        this.streamerUrl = streamerUrl;
    }

    public HttpResponse getResponse() {
        return response;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }
}
