package quant.rest;



import quant.http.client.TDClient;
import quant.http.requests.XMLRequestBuilder;
import quant.http.requests.builders.QuoteBuilder;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by dev on 2/3/15.
 */

@Path("/message")
public class Snapshot {


    public Snapshot() {}

    @Inject
    private TDClient client;

    @GET
    @Path("/{param}")
    public Response printMessage(@PathParam("param") String msg) {
        try {
            client =  InitialContext.doLookup("java:app/client-1.0-SNAPSHOT/TDClient");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        Future<Map> result;
        Map mapResults = null;

        XMLRequestBuilder xmlBuilder = client.XMLRequestBuilder();
        QuoteBuilder quoteBuilder = xmlBuilder.getQuoteBuilder();
        quoteBuilder.addSymbol(msg);
        try {
            result = client.execute(quoteBuilder.build());
            mapResults = result.get();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(mapResults == null) {
            return Response.status(200).entity("Did not work").build();
        }
        return Response.status(200).entity(mapResults.toString()).build();

    }
}