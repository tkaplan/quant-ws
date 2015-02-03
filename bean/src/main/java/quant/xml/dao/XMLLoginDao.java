package quant.xml.dao;

import org.apache.http.HttpResponse;
import org.w3c.dom.Document;
import quant.xml.entities.XMLLoginEntity;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

/**
 * Created by dev on 12/23/14.
 */
public class XMLLoginDao implements XMLEntityDao {
    private XMLLoginEntity entity;
    private HttpResponse response;

    @Override
    public Class getEntityClass() {
        return XMLLoginEntity.class;
    }

    @Override
    public XMLLoginEntity getEntity() {
        return entity;
    }

    @Override
    public HttpResponse getHttpResponse() {
        return response;
    }

    public XMLLoginEntity unmarshal (HttpResponse response) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream is = response.getEntity().getContent();
        this.response = response;

        entity = new XMLLoginEntity();

        if (is == null) {
            throw new Exception("Must set an input stream");
        }

        // Uri string
        Document doc = builder.parse(is);
        entity.setSegment(doc.getElementsByTagName("segment").item(0).getTextContent());
        entity.setAccountId(doc.getElementsByTagName("account-id").item(0).getTextContent());
        entity.setCompany(doc.getElementsByTagName("company").item(0).getTextContent());
        entity.setCdi(doc.getElementsByTagName("cdi").item(0).getTextContent());
        entity.setResponse(response);
        return entity;
    }
}

