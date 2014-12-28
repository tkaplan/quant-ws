package quant.xml.parser.responses.types;

import quant.xml.parser.annotations.BinaryData;
import quant.xml.parser.annotations.FieldData;

/**
 * Created by dev on 12/27/14.
 */
@BinaryData(
    name="volatility-history",
    data={
        @FieldData(name="symbol",type=String.class),
        @FieldData(name="error-code",type=Byte.class),
        @FieldData(name="error",type=String.class),
        @FieldData(name="repeat",type=VolatilityHistory.Repeat.class),
    }
)
public class VolatilityHistory {
    public VolatilityHistory() {

    }

    @BinaryData(
        name="repeat",
        data={
            @FieldData(name="value",type=Float.class),
            @FieldData(name="timestamp",type=Long.class)
        }
    )
    public class Repeat {
        public Repeat() {

        }
    }
}
