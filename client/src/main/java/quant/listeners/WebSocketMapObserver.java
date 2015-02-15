package quant.listeners;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import quant.http.client.TDClient;
import quant.services.ClientCount;
import quant.stream.iostream.MapObserver;

import javax.naming.InitialContext;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dev on 2/11/15.
 */
public class WebSocketMapObserver implements MapObserver {

    private MapObserver mo = null;
    private RemoteEndpoint.Async remote = null;
    private Class parseId = null;
    private Session client = null;
    private String[] parameters = null;
    private TDClient streamClient = null;
    private String refType = null;
    private ConcurrentHashMap chm = null;
    private String stringRequest = null;
    private String C = "";
    private String T = "";

    public WebSocketMapObserver(String request, Session client, ClientCount cc) throws Exception {
        // Get Session client for our websocket
        this.client = client;
        mo = this;
        remote = client.getAsyncRemote();

        // DI Stream Client
        streamClient =  InitialContext.doLookup("java:app/client-1.0-SNAPSHOT/TDClient");

        // Parse query
        parseQuery(request, cc);

        // Register symbols for traffic
        registerAllSymbols();

        // Update string request
        updateStringRequest();

        // Register ourselves
        streamClient.registerMapObserver(mo);

        // Update stream
        update();
    }


    private void parseQuery(String request, ClientCount cc) throws URISyntaxException {
        List<NameValuePair> params = URLEncodedUtils.parse(new URI(request), "UTF-8");
        for (NameValuePair param : params) {
            if(param.getName().equals("S")) {
                // Get the request type ... could be level 1/2, equities or options
                refType = param.getValue();
                chm = cc.getRefType(refType);
                parseId = streamClient.getParseIdForString(refType);
                T = streamClient.getT(refType);
                C = "ADD";
            } else if(param.getName().equals("P")) {
                // P gives us our symbols or parameters
                parameters = param.getValue().split("\\s*,\\s*");
            }
        }
    }

    // Register all symbols
    private void registerAllSymbols() {
        // Register all symbols
        for(String parameter: parameters) {
            Set<Session> clientSet = (Set<Session>) chm.get(parameter);

            // If symbol does not exist, create one
            if(clientSet == null) {
                clientSet = new HashSet<Session>();
                chm.put(parameter, clientSet);
            }

            // Add our client
            clientSet.add(client);
        }
    }

    // Remove all symbols
    private void unregisterAllSymbols() {
        for(String parameter: parameters) {
            Set<Session> clients = ((Set<Session>)chm.get(parameter));
            clients.remove(client);
            if(clients.isEmpty()) {
                // We want to unsubscribe to this particular symbol
                // for this service.
                C = "UNSUBS";
                updateStringRequest();
            }
        }
    }

    private void updateStringRequest() {
        String P = "";
        int length = parameters.length;
        for(int i = 0; i < length; i++) {
            if(i + 1 < length)
                P += parameters[i] + "+";
            else
                P += parameters[i];
        }
        stringRequest = "S=" + refType + "&C=" + C + "&P=" + P + "&T=" + T;
    }

    private synchronized void update() throws Exception {
        streamClient.updateStreaming(stringRequest);
    }

    @Override
    public Class getParseID() {
        return parseId;
    }

    @Override
    public Runnable onData(Map result) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    if(client.isOpen()) {
                        remote.sendText(result.toString());
                    } else {
                        unregisterAllSymbols();
                        updateStringRequest();
                        update();
                        streamClient.unregisterMapObserver(mo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
