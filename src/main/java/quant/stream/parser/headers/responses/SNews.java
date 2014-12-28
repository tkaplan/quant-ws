package quant.stream.parser.headers.responses;

import quant.stream.parser.annotations.Column;
import quant.stream.parser.annotations.RepeatableColumns;
import quant.stream.parser.headers.StreamingResponse;

import java.nio.ByteBuffer;

/**
 * Created by dev on 12/13/14.
 */
@RepeatableColumns(
        value={
                @Column(index=0,field="symbol",type=String.class),
                @Column(index=1,field="time",type=Integer.class),
                @Column(index=2,field="data",type=ByteBuffer.class),
                @Column(index=3,field="fieldSize",type=Integer.class)
        }
)
public class SNews extends StreamingResponse {
    private static final Short sid = (short)27;
}
