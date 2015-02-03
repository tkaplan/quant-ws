package quant.xml.factories;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import quant.xml.dao.XMLEntityDao;

import java.io.IOException;

/**
 * Created by dev on 12/23/14.
 */
public class ResponseHandlerFactory {

    public ResponseHandlerFactory () {

    }

    public static <DaoType extends XMLEntityDao> DaoType getResponseHandler(
        final DaoType dao,
        final HttpResponse response
    ) throws ClientProtocolException {
        final Class entityClass = dao.getEntityClass();

        Object xmlEntity = new Object();
        StatusLine statusLine = response.getStatusLine();
        HttpEntity entity = response.getEntity();
        if (statusLine.getStatusCode() >= 300) {
            throw new HttpResponseException(
                statusLine.getStatusCode(),
                statusLine.getReasonPhrase()
            );
        }

        if (entity == null) {
            throw new ClientProtocolException("Response Contains no content");
        }

        try {
            xmlEntity = entityClass.newInstance();
            dao.unmarshal(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dao;
    }

    // T is your dao type
    public static <DaoType extends XMLEntityDao> ResponseHandler getResponseHandler(final DaoType dao) {
        final Class entityClass = dao.getEntityClass();

        return new ResponseHandler<DaoType>() {
            @Override
            public DaoType handleResponse(
                final HttpResponse response
            ) throws IOException {
                Object xmlEntity = new Object();
                StatusLine statusLine = response.getStatusLine();
                HttpEntity entity = response.getEntity();
                if (statusLine.getStatusCode() >= 300) {
                    throw new HttpResponseException(
                        statusLine.getStatusCode(),
                        statusLine.getReasonPhrase()
                    );
                }

                if (entity == null) {
                    throw new ClientProtocolException("Response Contains no content");
                }

                try {
                    xmlEntity = entityClass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                try {
                    dao.unmarshal(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return dao;
            }
        };
    }

}