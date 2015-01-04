package quant.stream.parser.headers.responses;

import quant.stream.parser.annotations.Column;
import quant.stream.parser.annotations.RepeatableColumns;
import quant.stream.parser.headers.SnapshotResponse;

/**
 * Created by dev on 12/14/14.
 */
@RepeatableColumns(
        value={
                @Column(index=0,field="sid",type=Short.class),
                @Column(index=1,field="returnCode",type=Short.class),
                @Column(index=2,field="description",type=String.class)
        }
)
public class NGeneralResponse extends SnapshotResponse {
        private static final Short sid = (short)100;
}
