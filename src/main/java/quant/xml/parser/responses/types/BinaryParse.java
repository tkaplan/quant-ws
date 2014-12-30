package quant.xml.parser.responses.types;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by dev on 12/29/14.
 */
public interface BinaryParse {
    public Map<String, Object> parse(DataInputStream dis) throws IOException;
}
