package quant.stream.parser.headers;

import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by dev on 12/12/14.
 */
public class StreamingResponse {
    protected static final byte headerId = (byte)'S';
    protected short messageLength;
    protected short sid;

    private static Map<Short,Class> responses;

    private static Logger log = Logger.getLogger(StreamingResponse.class);

    public static void init() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        responses = ResponseUtil.init(StreamingResponse.class);
    }

    public static Map parse(DataInputStream dis) throws IOException, InvocationTargetException, IllegalAccessException {
        short messageLength = dis.readShort();
        short sid = dis.readShort();
        Class parseClass = responses.get(sid);
        Map<Object, Object> result = ResponseUtil.parseColumns(parseClass, dis);
        result.put("ParseID", parseClass);
        dis.readByte();
        return result;
    }
}
