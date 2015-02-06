package quant.rest;



import quant.http.client.TDClient;
import quant.http.requests.builders.PriceHistoryBuilder;
import quant.http.requests.builders.QuoteBuilder;
import quant.http.requests.builders.RequestBuilderInterface;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by dev on 2/3/15.
 */

@Path("/snapshot")
public class Snapshot {
    private TDClient client = null;
    private Map blank = new HashMap<>();
    public Snapshot() {
        try {
            client =  InitialContext.doLookup("java:app/client-1.0-SNAPSHOT/TDClient");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    private Response handle(RequestBuilderInterface builder) {
        Map results = null;
        try {
            Future<Map> result = client.execute(builder.build());
            results = result.get();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.
                status(400).
                entity("{error:" + e.getMessage() +"}").
                type(MediaType.APPLICATION_JSON_TYPE).
                build();
        }
        return Response.
            status(200).
            entity(results.toString()).
            type(MediaType.APPLICATION_JSON_TYPE).
            build();
    }

    @GET
    @Path("/price-history")
    public Response priceHistory(
        @QueryParam("request-value") String requestValue,
        @QueryParam("interval-type") String intervalType,
        @QueryParam("interval-duration") String intervalDuration,
        @QueryParam("start-date") String startDate,
        @QueryParam("end-date") String endDate
    ) {
        PriceHistoryBuilder priceHistory = client.
            XMLRequestBuilder().
            getPriceHistoryBuilder();

        priceHistory.setRequestValue(requestValue)
            .setIntervalType(intervalType)
            .setIntervalDuration(intervalDuration)
            .setStartDate(startDate)
            .setEndDate(endDate);

        return handle(priceHistory);
    }

    @GET
    @Path("/quotes")
    public Response priceHistory(
        @QueryParam("request-value") String symbols
    ) {
        QuoteBuilder quotes = client.
            XMLRequestBuilder().
            getQuoteBuilder();

        quotes.setSymbols(symbols);

        return handle(quotes);
    }
}