package quant.xml.entities;

import org.apache.http.HttpResponse;

/**
 * Created by dev on 12/23/14.
 */
public class XMLLoginEntity extends XMLEntity {
    private String segment;
    private String company;
    private String accountId;
    private String cdi;
    private HttpResponse response;

    public XMLLoginEntity () {

    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCdi() {
        return cdi;
    }

    public void setCdi(String cdi) {
        this.cdi = cdi;
    }

    public HttpResponse getResponse() {
        return response;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }
}
