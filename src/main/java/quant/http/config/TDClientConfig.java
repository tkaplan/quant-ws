package quant.http.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dev on 12/21/14.
 */
public class TDClientConfig {
    private Map<String, String> configMap;

    public TDClientConfig() {
        configMap = new HashMap<>();
        configMap.put("source", System.getenv("SOURCE"));
        configMap.put("version", System.getenv("VERSION"));
        configMap.put("userid", System.getenv("USERID"));
        configMap.put("host", System.getenv("HOST"));
        configMap.put("password", System.getenv("PASSWORD"));
    }

    public final String get(String key) {
        return configMap.get(key);
    }
}
