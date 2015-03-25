package quant.stream.manager;

import quant.stream.iostream.MapObserver;
import quant.stream.parser.headers.responses.NGeneralResponse;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by dev on 1/6/15.
 */
public class StatusObserver implements MapObserver {
    private final CountDownLatch latch;
    private StatusHolder status;

    public StatusObserver(CountDownLatch latch, StatusHolder status) {
        this.latch = latch;
        this.status = status;
    }

    @Override
    public Class getParseID() {
        return NGeneralResponse.class;
    }

    @Override
    public Runnable onData(Map result) {
        return new Runnable() {
            @Override
            public void run() {
                if((short)result.get("sid") == (short)100
                    &&
                    ((String)result.get("description")).equals("SUCCESS")) {
                    status.set(Status.OK);
                } else {
                    status.set(Status.ERROR);
                }
                latch.countDown();
            }
        };
    }
}
