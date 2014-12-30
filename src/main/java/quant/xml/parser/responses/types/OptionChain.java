package quant.xml.parser.responses.types;

import quant.xml.parser.annotations.Node;
import quant.xml.parser.annotations.ParentXML;
import quant.xml.parser.annotations.Root;

/**
 * Created by dev on 12/27/14.
 */
@ParentXML
@Root(
    name="amtd",
    nodes={
        @Node(name="option-chain-results",type=OptionChain.OptionChainResults.class)
    }
)
public class OptionChain {
    public OptionChain() {

    }

    @Root(
        name="option-chain-results",
        nodes={
            @Node(name="symbol",type=String.class),
            @Node(name="description",type=String.class),
            @Node(name="bid",type=Double.class),
            @Node(name="ask",type=Double.class),
            @Node(name="bid-ask-size",type=String.class),
            @Node(name="last",type=Double.class),
            @Node(name="open",type=Double.class),
            @Node(name="high",type=Double.class),
            @Node(name="low",type=Double.class),
            @Node(name="close",type=Double.class),
            @Node(name="volume",type=Integer.class),
            @Node(name="change",type=Double.class),
            @Node(name="quote-punctuality",type=String.class),
            @Node(name="time",type=String.class),
            @Node(name="option-date",type=OptionChainResults.OptionDate.class)
        }
    )
    public class OptionChainResults {
        public OptionChainResults() {

        }

        @Root(
            name="option-date",
            nodes={
                @Node(name="date",type=String.class),
                @Node(name="expiration-type",type=String.class),
                @Node(name="days-to-expiration",type=Integer.class),
                @Node(name="option-strike",type=OptionDate.OptionStrike.class)
            }
        )
        public class OptionDate {
            public OptionDate() {

            }

            @Root(
                name="option-strike",
                nodes={
                    @Node(name="strike-price",type=Double.class),
                    @Node(name="standard-option",type=Boolean.class),
                    @Node(name="put",type=OptionStrike.Put.class),
                    @Node(name="call",type=OptionStrike.Call.class)
                }
            )
            public class OptionStrike {
                public OptionStrike () {

                }

                @Root(
                    name="row",
                    nodes={
                        @Node(name="symbol",type=String.class),
                        @Node(name="shares",type=Integer.class)
                    }
                )
                public class Row {

                }

                @Root(
                    name="deliverable-list",
                    nodes={
                        @Node(name="notes-description",type=String.class),
                        @Node(name="cash-in-lieu-dollar-amount",type=Double.class),
                        @Node(name="cash-dollar-amount",type=Double.class),
                        @Node(name="index-option",type=Boolean.class),
                        @Node(name="row",type=Row.class)
                    }
                )
                public class DeliverableList {

                }

                @Root(
                    name="put",
                    nodes= {
                        @Node(name = "symbol", type = String.class),
                        @Node(name="option-symbol",type=String.class),
                        @Node(name = "description", type = String.class),
                        @Node(name="bid",type=Double.class),
                        @Node(name="ask",type=Double.class),
                        @Node(name="bid-ask-size",type=String.class),
                        @Node(name="last",type=Double.class),
                        @Node(name="last-trade-date",type=String.class),
                        @Node(name="volume",type=Integer.class),
                        @Node(name="open-interest",type=Integer.class),
                        @Node(name="real-time",type=String.class),
                        @Node(name="underlying-symbol",type=String.class),
                        @Node(name="delta",type=Double.class),
                        @Node(name="gamma",type=Double.class),
                        @Node(name="theta",type=Double.class),
                        @Node(name="vega",type=Double.class),
                        @Node(name="rho",type=Double.class),
                        @Node(name="implied-volatility",type=Double.class),
                        @Node(name="time-value-index",type=Double.class),
                        @Node(name="multiplier",type=Integer.class),
                        @Node(name="change",type=Double.class),
                        @Node(name="change-percentage",type=String.class),
                        @Node(name="in-the-money",type=Boolean.class),
                        @Node(name="near-the-money",type=Boolean.class),
                        @Node(name="theoretical-value",type=Double.class),
                        @Node(name="deliverable-list",type=DeliverableList.class)
                    }
                )
                public class Put {

                }

                @Root(
                    name="call",
                    nodes= {
                        @Node(name = "symbol", type = String.class),
                        @Node(name="option-symbol",type=String.class),
                        @Node(name = "description", type = String.class),
                        @Node(name="bid",type=Double.class),
                        @Node(name="ask",type=Double.class),
                        @Node(name="bid-ask-size",type=String.class),
                        @Node(name="last",type=Double.class),
                        @Node(name="last-trade-date",type=String.class),
                        @Node(name="volume",type=Integer.class),
                        @Node(name="open-interest",type=Integer.class),
                        @Node(name="real-time",type=String.class),
                        @Node(name="underlying-symbol",type=String.class),
                        @Node(name="delta",type=Double.class),
                        @Node(name="gamma",type=Double.class),
                        @Node(name="theta",type=Double.class),
                        @Node(name="vega",type=Double.class),
                        @Node(name="rho",type=Double.class),
                        @Node(name="implied-volatility",type=Double.class),
                        @Node(name="time-value-index",type=Double.class),
                        @Node(name="multiplier",type=Integer.class),
                        @Node(name="change",type=Double.class),
                        @Node(name="change-percentage",type=String.class),
                        @Node(name="in-the-money",type=Boolean.class),
                        @Node(name="near-the-money",type=Boolean.class),
                        @Node(name="theoretical-value",type=Double.class),
                        @Node(name="deliverable-list",type=DeliverableList.class)
                    }
                )
                public class Call {

                }
            }
        }
    }
}
