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
                @Column(index=1,field="contract",type=String.class),
                @Column(index=2,field="bid",type=Float.class),
                @Column(index=3,field="ask",type=Float.class),
                @Column(index=4,field="last",type=Float.class),
                @Column(index=5,field="high",type=Float.class),
                @Column(index=6,field="low",type=Float.class),
                @Column(index=7,field="close",type=Float.class),
                @Column(index=8,field="volume",type=Long.class),
                @Column(index=9,field="openInterest",type=Integer.class),
                @Column(index=10,field="volatility",type=Float.class),
                @Column(index=11,field="quotetime",type=Integer.class),
                @Column(index=12,field="tradetime",type=Integer.class),
                @Column(index=13,field="inTheMoney",type=Float.class),
                @Column(index=14,field="quoteDate",type=Integer.class),
                @Column(index=15,field="tradeDate",type=Integer.class),
                @Column(index=16,field="year",type=Integer.class),
                @Column(index=17,field="multiplier",type=Float.class),
                @Column(index=19,field="open",type=Float.class),
                @Column(index=20,field="bidsize",type=Integer.class),
                @Column(index=21,field="asksize",type=Integer.class),
                @Column(index=22,field="lastsize",type=Integer.class),
                @Column(index=23,field="change",type=Float.class),
                @Column(index=24,field="strike",type=Float.class),
                @Column(index=25,field="contractType",type=Character.class),
                @Column(index=26,field="underlying",type=String.class),
                @Column(index=27,field="month",type=Integer.class),
                @Column(index=28,field="note",type=String.class),
                @Column(index=29,field="timevalue",type=Float.class),
                @Column(index=31,field="daysToExp",type=Integer.class),
                @Column(index=32,field="deltaIndex",type=Float.class),
                @Column(index=33,field="gammaIndex",type=Float.class),
                @Column(index=34,field="thetaIndex",type=Float.class),
                @Column(index=35,field="vegaIndex",type=Float.class),
                @Column(index=36,field="rhoIndex",type=Float.class)
        }
)
public class SLevel1Option extends StreamingResponse {
    private static final Short sid = (short)18;
}
