package quant.rest;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import quant.http.client.TDClient;
import quant.listeners.WebSocketMapObserver;
import quant.services.ClientCount;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


/**
 * Created by dev on 2/10/15.
 */
@ServerEndpoint("/stream")
public class Stream {
    private TDClient client = null;
    private ClientCount cc = null;

    public Stream() {
        cc = new ClientCount();
        try {
            client =  InitialContext.doLookup("java:app/client-1.0-SNAPSHOT/TDClient");
            client.startStreaming();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void update(String message, Session client) {
        try {
            // Automatically registers itself
            new WebSocketMapObserver(message, client, cc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void myOnOpen(Session session) {
        System.out.println("WebSocket opened: " + session.getId());
    }

    @OnClose
    public void myOnClose(CloseReason reason) {
        System.out.println("Closing a WebSocket due to: " + reason.getReasonPhrase());
    }
}
