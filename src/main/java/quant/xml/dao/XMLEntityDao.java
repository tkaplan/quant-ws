package quant.xml.dao;

import org.apache.http.HttpResponse;

/**
 * Created by dev on 12/23/14.
 */
public interface XMLEntityDao {
    public Class getEntityClass();
    public Object unmarshal(HttpResponse entity) throws Exception;
    public Object getEntity();
    public HttpResponse getHttpResponse();
}
