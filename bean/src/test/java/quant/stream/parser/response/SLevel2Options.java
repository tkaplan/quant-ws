package quant.stream.parser.response;

import quant.stream.parser.ResponseTest;
import quant.stream.parser.headers.responses.SLevel2OPNY;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by dev on 2/18/15.
 */
@ResponseTest
public class SLevel2Options implements MapObserverTest {
    private final CountDownLatch latch;
    private final CountDownLatch assertLatch;

    public SLevel2Options(CountDownLatch latch, CountDownLatch assertLatch) {
        this.latch = latch;
        this.assertLatch = assertLatch;
    }

    @Override
    public Class getParseID() {
        return SLevel2OPNY.class;
    }

    @Override
    public Runnable onData(Map result) {
        return new Runnable() {
            @Override
            public void run() {
                System.out.println("Running SLevel2OPNYObs");
                // Always assert the latch before
                // release latch
                if(!result.get("symbol").equals("TSN_022015P35"))
                    assertLatch.countDown();
                if(!result.get("ParseID").equals(SLevel2OPNY.class))
                    assertLatch.countDown();
                latch.countDown();
            }
        };
    }

    @Override
    public String request() {
        return "S=OPRA&C=ADD&P=TSN_022015P35&T=0+1+2";
    }
}
