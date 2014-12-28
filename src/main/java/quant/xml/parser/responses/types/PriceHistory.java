package quant.xml.parser.responses.types;

import quant.xml.parser.annotations.BinaryData;
import quant.xml.parser.annotations.FieldData;

/**
 * Created by dev on 12/27/14.
 */
@BinaryData(
    name="price-history",
    data={
        @FieldData(name="symbol",type=String.class),
        @FieldData(name="error-code",type=Byte.class),
        @FieldData(name="error-text",type=String.class),
        @FieldData(name="repeat",type=PriceHistory.Repeat.class)
    }
)
public class PriceHistory {
    public PriceHistory() {

    }

    @BinaryData(
        name="repeat",
        data={
            @FieldData(name="close",type=Float.class),
            @FieldData(name="high",type=Float.class),
            @FieldData(name="low",type=Float.class),
            @FieldData(name="open",type=Float.class),
            @FieldData(name="volume",type=Float.class),
            @FieldData(name="timestamp",type=Long.class)
        }
    )
    public class Repeat {

    }
}
