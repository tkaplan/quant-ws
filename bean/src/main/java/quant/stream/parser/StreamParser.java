package quant.stream.parser;

import quant.stream.parser.headers.HeaderManager;

import java.io.DataInputStream;
import java.util.Map;

/**
 * Created by dev on 12/10/14.
 */
public class StreamParser {
    // New stream parser
    public static Map parse(DataInputStream dis) throws Exception {
        byte header = dis.readByte();
        // We want the parser to return an object...
        // How we will manage this object I don't
        // know, but we'll just throw it in a set
        // for now.
        return HeaderManager.parse(header, dis);
    }

    public static void init() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        HeaderManager.init();
    }
}
