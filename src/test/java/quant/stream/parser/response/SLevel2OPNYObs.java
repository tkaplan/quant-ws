package quant.stream.parser.response;

/**
 * Created by dev on 1/6/15.
 */

import quant.stream.parser.ResponseTest;
import quant.stream.parser.headers.responses.SLevel2Nasdaq;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

import quant.stream.iostream.MapObserver;
import quant.stream.parser.ResponseTest;
import quant.stream.parser.headers.responses.SLevel2Nasdaq;
import quant.stream.parser.headers.responses.SLevel2OPNY;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by dev on 1/6/15.
 */
@ResponseTest
public class SLevel2OPNYObs implements MapObserverTest {
    private final CountDownLatch latch;
    private final CountDownLatch assertLatch;

    public SLevel2OPNYObs(CountDownLatch latch, CountDownLatch assertLatch) {
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
                if(!result.get("symbol").equals("IBM"))
                    assertLatch.countDown();
                if(!result.get("ParseID").equals(SLevel2OPNY.class))
                    assertLatch.countDown();
                latch.countDown();
            }
        };
    }

    @Override
    public String request() {
        return "S=NYSE_BOOK&C=SUBS&P=IBM&T=0+1";
    }
}
