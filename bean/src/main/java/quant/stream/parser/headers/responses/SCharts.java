package quant.stream.parser.headers.responses;

import quant.stream.parser.annotations.Column;
import quant.stream.parser.annotations.RepeatableColumns;
import quant.stream.parser.headers.StreamingResponse;

/**
 * Created by dev on 12/13/14.
 */
@RepeatableColumns(
        value={
                @Column(index=0,field="symbol",type=String.class),
                @Column(index=1,field="sequence",type=Integer.class),
                @Column(index=2,field="open",type=Float.class),
                @Column(index=3,field="high",type=Float.class),
                @Column(index=4,field="low",type=Float.class),
                @Column(index=5,field="close",type=Float.class),
                @Column(index=6,field="volume",type=Integer.class),
                @Column(index=7,field="time",type=Integer.class),
                @Column(index=8,field="date",type=Integer.class)
        }
)
public class SCharts extends StreamingResponse {
    private static final Short[] sids = {(short)82,(short)83,(short)85};
}
