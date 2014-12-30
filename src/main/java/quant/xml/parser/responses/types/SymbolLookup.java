package quant.xml.parser.responses.types;

import quant.xml.parser.annotations.Node;
import quant.xml.parser.annotations.ParentXML;
import quant.xml.parser.annotations.Root;

/**
 * Created by dev on 12/25/14.
 */
@ParentXML
@Root(
    name="amtd",
    nodes={
        @Node(name="symbol-lookup-result", type=SymbolLookup.SymbolLookupResult.class)
    }
)
public class SymbolLookup {
    @Root(
        name="symbol-lookup-result",
        nodes={
            @Node(name="search-string",type=String.class),
            @Node(name="symbol-result",type=SymbolLookupResult.SymbolResult.class)
        }
    )
    public class SymbolLookupResult {
        public SymbolLookupResult() {

        }

        @Root(
            name="symbol-result",
            nodes={
                @Node(name="symbol",type=String.class),
                @Node(name="description",type=String.class)
            }
        )
        public class SymbolResult {

        }
    }
}
