package quant.stream.parser.headers.responses;

import quant.stream.parser.annotations.Column;
import quant.stream.parser.annotations.RepeatableColumns;
import quant.stream.parser.headers.StreamingResponse;

/**
 * Created by dev on 12/13/14.
 */
@RepeatableColumns(
        value={
                @Column(index=0,field="subscriptionKey",type=String.class),
                @Column(index=1,field="accountNumber",type=Integer.class),
                @Column(index=2,field="messageType",type=Float.class),
                @Column(index=3,field="messageData",type=Float.class),
                @Column(index=4,field="messageData",type=Integer.class),
                @Column(index=5,field="messageData",type=Integer.class)
        }
)
public class STimeSale extends StreamingResponse {
    private static final Short sid = (short)5;
}
