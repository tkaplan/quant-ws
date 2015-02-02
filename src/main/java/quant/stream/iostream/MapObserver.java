package quant.stream.iostream;

import java.util.Map;

/**
 * Created by dev on 1/5/15.
 */
public interface MapObserver {
    public Class getParseID();
    public Runnable onData(Map result);
}
