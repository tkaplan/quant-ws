package quant.rest;

/**
 * Created by dev on 2/3/15.
 */
import quant.http.client.TDClient;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/example")
public class QuantRest extends Application {
    //@Inject
  //  @Any
//    Instance<Object> myBeans;

    private Set<Object> singletons = new HashSet<Object>();

    public QuantRest() {
        singletons.add(new Snapshot());
        //myBeans.select(TDClient.class).get();
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
