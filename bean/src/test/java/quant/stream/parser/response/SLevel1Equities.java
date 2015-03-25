package quant.stream.parser.response;
import quant.stream.parser.ResponseTest;
import quant.stream.parser.headers.responses.SLevel1Equity;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

//@ResponseTest
public class SLevel1Equities implements MapObserverTest {

    private final CountDownLatch latch;
    private final CountDownLatch assertLatch;

    public SLevel1Equities(CountDownLatch latch, CountDownLatch assertLatch) {
        this.latch = latch;
        this.assertLatch = assertLatch;
    }

    @Override
    public Class getParseID() {
        return SLevel1Equity.class;
    }

    @Override
    public Runnable onData(Map result) {
        return new Runnable() {
            @Override
            public void run() {
                // Always assert the latch before
                // release latch
                if(!result.get("symbol").equals("APPL"))
                    assertLatch.countDown();
                if(!result.get("ParseID").equals(SLevel1Equity.class))
                    assertLatch.countDown();
                latch.countDown();
            }
        };
    }

    @Override
    public String request() {
        return "S=QUOTE&C=SUBS&P=APPL&T=0+1+2+3+8+10+11+12+13+15+16+18";
    }
}


