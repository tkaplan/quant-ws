package quant.stream.parser.headers.responses;

import quant.stream.parser.annotations.Column;
import quant.stream.parser.annotations.RepeatableColumns;
import quant.stream.parser.headers.StreamingResponse;

/**
 * Created by dev on 12/12/14.
 */
@RepeatableColumns(
        value={
                @Column(index=0,field="symbol",type=String.class),
                @Column(index=1,field="bid",type=Float.class),
                @Column(index=2,field="ask",type=Float.class),
                @Column(index=3,field="last",type=Float.class),
                @Column(index=4,field="bidsize",type=Integer.class),
                @Column(index=5,field="asksize",type=Integer.class),
                @Column(index=6,field="bidid",type=Character.class),
                @Column(index=7,field="askid",type=Character.class),
                @Column(index=8,field="volume",type=Long.class),
                @Column(index=9,field="lastsize",type=Integer.class),
                @Column(index=10,field="tradetime",type=Integer.class),
                @Column(index=11,field="quotetime",type=Integer.class),
                @Column(index=12,field="high",type=Float.class),
                @Column(index=13,field="low",type=Float.class),
                @Column(index=14,field="tick",type=Character.class),
                @Column(index=15,field="close",type=Float.class),
                @Column(index=16,field="exchange",type=Character.class),
                @Column(index=17,field="marginable",type=Boolean.class),
                @Column(index=18,field="shortable",type=Boolean.class),
                @Column(index=22,field="quotedate",type=Integer.class),
                @Column(index=23,field="tradedate",type=Integer.class),
                @Column(index=24,field="volatility",type=Float.class),
                @Column(index=25,field="description",type=String.class),
                @Column(index=26,field="tradeid",type=Character.class),
                @Column(index=27,field="digits",type=Integer.class),
                @Column(index=28,field="open",type=Float.class),
                @Column(index=29,field="change",type=Float.class),
                @Column(index=30,field="weekHigh52",type=Float.class),
                @Column(index=31,field="weekLow52",type=Float.class),
                @Column(index=32,field="peRation",type=Float.class),
                @Column(index=33,field="dividendAMT",type=Float.class),
                @Column(index=34,field="dividendYield",type=Float.class),
                @Column(index=37,field="nav",type=Float.class),
                @Column(index=38,field="fund",type=Float.class),
                @Column(index=39,field="exchangeName",type=String.class),
                @Column(index=40,field="dividendDate",type=String.class),
                @Column(index=41,field="lastMarketHours",type=Float.class),
                @Column(index=42,field="lastsizeMarketHours",type=Integer.class),
                @Column(index=43,field="tradedateMarketHours",type=Integer.class),
                @Column(index=44,field="tradetimeMarketHours",type=Integer.class),
                @Column(index=45,field="changeMarketHours",type=Float.class),
                @Column(index=46,field="isRegularMarketQuote",type=Boolean.class),
                @Column(index=47,field="isRegularMarketTrade",type=Boolean.class)
        }
)
public class SLevel1Equity extends StreamingResponse {
    private static final Short sid = (short)1;
}
