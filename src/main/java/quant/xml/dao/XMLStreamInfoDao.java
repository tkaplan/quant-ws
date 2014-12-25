package quant.xml.dao;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.w3c.dom.Document;
import quant.xml.entities.XMLStreamInfoEntity;
import sun.misc.IOUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Created by dev on 12/23/14.
 */
public class XMLStreamInfoDao implements XMLEntityDao {
    private XMLStreamInfoEntity entity;
    private HttpResponse response;

    @Override
    public Class getEntityClass () {
        return XMLStreamInfoEntity.class;
    }

    @Override
    public XMLStreamInfoEntity getEntity () {
        return entity;
    }

    @Override
    public HttpResponse getHttpResponse() {
        return response;
    }

    public XMLStreamInfoEntity unmarshal (HttpResponse response) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream is = response.getEntity().getContent();
        this.response = response;

        entity = new XMLStreamInfoEntity();

        if (is == null) {
            throw new Exception("Must set an input stream");
        }

        // Uri string
        Document doc = builder.parse(is);
        entity.setStreamerUrl(doc.getElementsByTagName("streamer-url").item(0).getTextContent());
        entity.setToken(doc.getElementsByTagName("token").item(0).getTextContent());
        entity.setTimestamp(doc.getElementsByTagName("timestamp").item(0).getTextContent());
        entity.setCdDomainId(doc.getElementsByTagName("cd-domain-id").item(0).getTextContent());
        entity.setUsergroup(doc.getElementsByTagName("usergroup").item(0).getTextContent());
        entity.setAccesslevel(doc.getElementsByTagName("access-level").item(0).getTextContent());
        entity.setAcl(doc.getElementsByTagName("acl").item(0).getTextContent());
        entity.setAppId(doc.getElementsByTagName("app-id").item(0).getTextContent());
        entity.setAuthorized(doc.getElementsByTagName("authorized").item(0).getTextContent());
        entity.setResponse(response);
        return entity;
    }
}
