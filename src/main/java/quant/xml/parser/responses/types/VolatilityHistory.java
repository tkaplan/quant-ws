package quant.xml.parser.responses.types;

import quant.xml.parser.annotations.BinaryData;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by dev on 12/27/14.
 */
@BinaryData
public class VolatilityHistory implements BinaryParse {
    public VolatilityHistory() {

    }

    @Override
    public Map<String, Object> parse(DataInputStream dis) throws IOException {
        return null;
    }
}