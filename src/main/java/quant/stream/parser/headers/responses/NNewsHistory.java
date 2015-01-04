package quant.stream.parser.headers.responses;

import quant.stream.parser.annotations.Column;
import quant.stream.parser.annotations.RepeatableColumns;
import quant.stream.parser.headers.SnapshotResponse;

import java.nio.ByteBuffer;

/**
 * Created by dev on 12/13/14.
 */
@RepeatableColumns(
        value={
                @Column(index=0,field="symbol",type=String.class),
                @Column(index=1,field="data",type=ByteBuffer.class)
        }
)
public class NNewsHistory extends SnapshotResponse {
        private static final Short sid = (short)28;
}
