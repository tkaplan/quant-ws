package quant.stream.parser.response;

import quant.stream.parser.ResponseTest;
import quant.stream.parser.headers.responses.SLevel2Nasdaq;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by dev on 1/6/15.
 */
@ResponseTest
public class SLevel2NasdaqObs implements MapObserverTest {
    private final CountDownLatch latch;
    private final CountDownLatch assertLatch;

    public SLevel2NasdaqObs(CountDownLatch latch, CountDownLatch assertLatch) {
        this.latch = latch;
        this.assertLatch = assertLatch;
    }

    @Override
    public Class getParseID() {
        return SLevel2Nasdaq.class;
    }

    @Override
    public Runnable onData(Map result) {
        return new Runnable() {
            @Override
            public void run() {
                // Always assert the latch before
                // release latch
                if(!result.get((short)0).equals("ADXS>L2"))
                    assertLatch.countDown();
                if(!result.get("ParseID").equals(SLevel2Nasdaq.class))
                    assertLatch.countDown();
                latch.countDown();
            }
        };
    }

    @Override
    public String request() {
        return "S=TOTAL_VIEW&C=ADD&P=ADXS>L2&T=0+1+2+3";
    }
}
