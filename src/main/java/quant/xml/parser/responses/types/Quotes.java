package quant.xml.parser.responses.types;

import quant.xml.parser.annotations.Node;
import quant.xml.parser.annotations.ParentXML;
import quant.xml.parser.annotations.Root;

import java.util.Map;

/**
 * Created by dev on 12/24/14.
 */
@ParentXML
@Root(
    name="amtd",
    nodes={
        @Node(name="quote-list",type=Quotes.QuoteList.class)
    }
)
public class Quotes {
    public Quotes() {

    }

    @Root(
        name="quote-list",
        nodes={
            @Node(name="quote",type=QuoteList.Quote.class)
        }
    )
    public class QuoteList {
        public QuoteList() {

        }

        @Root(
            name="quote",
            nodes={
                @Node(name="symbol",type=String.class),
                @Node(name="description",type=String.class),
                @Node(name="bid",type=Double.class),
                @Node(name="ask",type=Double.class),
                @Node(name="bid-ask-size",type=String.class),
                @Node(name="last",type=Double.class),
                @Node(name="last-trade-size",type=Integer.class),
                @Node(name="last-trade-date",type=String.class),
                @Node(name="open",type=Double.class),
                @Node(name="high",type=Double.class),
                @Node(name="low",type=Double.class),
                @Node(name="nav",type=Double.class),
                @Node(name="offer",type=Double.class),
                @Node(name="close",type=Double.class),
                @Node(name="volume",type=Integer.class),
                @Node(name="strike-price",type=Double.class),
                @Node(name="open-interest",type=Integer.class),
                @Node(name="expiration-month",type=Integer.class),
                @Node(name="expiration-day",type=Integer.class),
                @Node(name="expiration-year",type=Integer.class),
                @Node(name="exchange",type=String.class),
                @Node(name="underlying-symbol",type=String.class),
                @Node(name="put-call",type=String.class),
                @Node(name="delta",type=Double.class),
                @Node(name="gamma",type=Double.class),
                @Node(name="theta",type=Double.class),
                @Node(name="vega",type=Double.class),
                @Node(name="rho",type=Double.class),
                @Node(name="implied-volatility",type=Double.class),
                @Node(name="days-to-expiration",type=Integer.class),
                @Node(name="time-value-index",type=Double.class),
                @Node(name="multiplier",type=Integer.class),
                @Node(name="year-high",type=Double.class),
                @Node(name="year-low",type=Double.class),
                @Node(name="reql-time",type=String.class),
                @Node(name="exchange",type=String.class),
                @Node(name="asset-type",type=String.class),
                @Node(name="change",type=Double.class),
                @Node(name="change-percent",type=String.class)
            }
        )
        public class Quote {

        }
    }
}
