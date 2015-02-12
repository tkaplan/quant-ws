package quant.services;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dev on 2/11/15.
 */
public class ClientCount {
    private Map<String, ConcurrentHashMap> refType;

    public ClientCount() {
        refType = new HashMap<>();

        // Level 1 Equity: set is a set of clients
        refType.put("QUOTE", new ConcurrentHashMap<String, Set<Session>>());

        // Level 1 Option
        refType.put("OPTION", new ConcurrentHashMap<String, Set<Session>>());

        // Level 2 Option Quotes
        refType.put("OPRA", new ConcurrentHashMap<String, Set<Session>>());

        // NYSE_BOOK Quotes
        refType.put("NYSE_BOOK", new ConcurrentHashMap<String, Set<Session>>());

        // NASDAQ Quotes
        refType.put("TOTAL_VIEW", new ConcurrentHashMap<String, Set<Session>>());
    }

    public ConcurrentHashMap getRefType(String key) {
        return refType.get(key);
    }
}
