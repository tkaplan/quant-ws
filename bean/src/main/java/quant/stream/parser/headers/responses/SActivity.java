package quant.stream.parser.headers.responses;

import quant.stream.parser.annotations.Column;
import quant.stream.parser.annotations.RepeatableColumns;
import quant.stream.parser.headers.StreamingResponse;

/**
 * Created by dev on 12/13/14.
 */
@RepeatableColumns(
        value={
                @Column(index=0,field="activitiesType",type=String.class),
                @Column(index=1,field="data",type=String.class)
        }
)
public class SActivity extends StreamingResponse {
    private static final Short[] sids = {(short)23,(short)25,(short)26,(short)35};
}
