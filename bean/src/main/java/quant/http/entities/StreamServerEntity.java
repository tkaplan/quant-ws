package quant.http.entities;

/**
 * Created by dev on 12/23/14.
 */
public class StreamServerEntity {
    // !U account-id : LogIn
    // A=userid account-id : LogIn
    private String accountId;

    // company company : LogIn
    private String company;

    // segment segment : LogIn
    private String segment;

    // W token : streamInfo
    // token token : streamInfo
    private String token;

    // cddomain cd-domain-id : streamInfo
    private String cddomain;

    // usergroup usergroup : streamInfo
    private String usergroup;

    // accesslevel access-level : streamInfo
    private String accesslevel;

    // authorized authorized : streamInfo
    private String authorized;

    // acl acl : streamInfo
    private String acl;

    // timestamp timestamp : streamInfo
    private String timestamp;

    // appid app-id : streamInfo
    private String appId;

    private String streamUrl;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCddomain() {
        return cddomain;
    }

    public void setCddomain(String cddomain) {
        this.cddomain = cddomain;
    }

    public String getUsergroup() {
        return usergroup;
    }

    public void setUsergroup(String usergroup) {
        this.usergroup = usergroup;
    }

    public String getAccesslevel() {
        return accesslevel;
    }

    public void setAccesslevel(String accesslevel) {
        this.accesslevel = accesslevel;
    }

    public String getAcl() {
        return acl;
    }

    public void setAcl(String acl) {
        this.acl = acl;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getAuthorized() {
        return authorized;
    }

    public void setAuthorized(String authorized) {
        this.authorized = authorized;
    }
}
