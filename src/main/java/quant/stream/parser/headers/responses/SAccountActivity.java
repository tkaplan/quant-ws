package quant.stream.parser.headers.responses;

import quant.stream.parser.annotations.Column;
import quant.stream.parser.annotations.RepeatableColumns;
import quant.stream.parser.headers.StreamingResponse;

/**
 * Created by dev on 12/12/14.
 */
@RepeatableColumns(
        value={
                @Column(index=0,field="subscriptionKey",type=String.class),
                @Column(index=1,field="accountNumber",type=String.class),
                @Column(index=2,field="messageType",type=String.class),
                @Column(index=3,field="messageData",type=String.class)
        }
)
public class SAccountActivity extends StreamingResponse {
    private static final Short sid = (short)90;
}
