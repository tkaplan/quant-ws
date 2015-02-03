package quant.xml.parser.responses;

import quant.xml.parser.annotations.BinaryData;
import sun.misc.IOUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.InflaterInputStream;

/**
 * Created by dev on 12/27/14.
 */
@BinaryData
public class PriceHistory {
    public PriceHistory() {

    }

    public static Map<String, Object> parse(InputStream is) throws IOException {
        DataInputStream dis = new DataInputStream(is);
        Map<String, Object> entity = new HashMap<>();
        Integer symbolCount = dis.readInt();
        while(symbolCount-- > 0) {
            Map<String, Object> symbol = new HashMap<>();
            int strLen = dis.readShort();
            byte[] symbolName = new byte[strLen];
            dis.readFully(symbolName);
            byte errorCode = dis.readByte();
            if(errorCode > 0) {
                short errorLength = dis.readShort();
                byte[] error = new byte[errorLength];
                dis.readFully(error);
                symbol.put("error", error);
            }
            int barCount = dis.readInt();
            List<Map<String, Object>> quotes = new ArrayList<>();
            while(barCount-- > 0) {
                Map<String, Object> quote = new HashMap<>();
                quote.put("close", dis.readFloat());
                quote.put("high", dis.readFloat());
                quote.put("low",dis.readFloat());
                quote.put("open",dis.readFloat());
                quote.put("volume",dis.readFloat());
                quote.put("timestamp", dis.readLong());
                quotes.add(quote);
            }
            symbol.put("quotes", quotes);
            entity.put(new String(symbolName, "UTF-8"), symbol);
            dis.readShort();
        }
        return entity;
    }
}
