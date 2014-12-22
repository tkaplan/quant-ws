package quant.http.config;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dev on 12/21/14.
 */
@Startup
@Singleton(
    description="Shared async client config",
    mappedName="TDClientConfig",
    name="TDClientConfig"
)
public class TDClientConfig {
    private Map<String, String> configMap;

    public String get(String key) {
        final String ret = configMap.get(key);
        return ret;
    }

    @PostConstruct
    public void startup() {
        configMap = new HashMap<>();
        configMap.put("source", System.getenv("SOURCE"));
        configMap.put("version", System.getenv("VERSION"));
        configMap.put("userid", System.getenv("USERID"));
        configMap.put("host", System.getenv("HOST"));
        configMap.put("password", System.getenv("PASSWORD"));
    }
}
