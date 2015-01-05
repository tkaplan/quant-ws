package quant.stream.parser.headers;

import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by dev on 12/12/14.
 */
public class SnapshotResponse {

    private static Map<Short,Class> responses;

    private static Logger log = Logger.getLogger(SnapshotResponse.class);


    public static void init() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        responses = ResponseUtil.init(SnapshotResponse.class);
    }

    public static Map parse(DataInputStream dis) throws IOException, InvocationTargetException, IllegalAccessException {
        short snapshotIDLength = getSnapshotIDLength(dis);
        short sid = getSnapshotId(dis, snapshotIDLength);
        int messageLength = getMessageLength(dis);
        Class parseClass = responses.get(sid);
        dis.readShort();
        Map<Object, Object> result = ResponseUtil.parseColumns(parseClass, dis);
        result.put("ParseID", parseClass);
        dis.readByte();
        return result;
    }

    private static short getSnapshotIDLength(DataInputStream dis) throws IOException {
        return dis.readShort();
    }

    private static short getSnapshotId(DataInputStream dis, Short sidLength) throws IOException {
        byte[] sid = new byte[sidLength];
        dis.readFully(sid);
        String sids = new String(sid,"UTF-8");
        return Short.parseShort(sids);
    }

    private static int getMessageLength(DataInputStream dis) throws IOException {
        return dis.readInt();
    }
}
