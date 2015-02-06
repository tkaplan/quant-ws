package quant.rest;

/**
 * Created by dev on 2/3/15.
 */
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class QuantRest extends Application {

    private Set<Object> singletons = new HashSet<Object>();

    public QuantRest() {
        singletons.add(new Snapshot());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
