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
    protected static final byte headerId = (byte)'N';
    protected short snapshotIDLength;
    protected byte[] snapshotId;
    protected int payloadLength;
    protected static final byte delimiter = (byte)0xFF;
    protected static final byte endDelimiter = (byte)0x0A;

    private static Map<Short,Class> responses;

    private static Logger log = Logger.getLogger(SnapshotResponse.class);


    public static void init() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        responses = ResponseUtil.init(SnapshotResponse.class);
    }

    public static void parse(DataInputStream dis) throws IOException, InvocationTargetException, IllegalAccessException {
        short snapshotIDLength = getSnapshotIDLength(dis);
        short sid = getSnapshotId(dis, snapshotIDLength);
        int messageLength = getMessageLength(dis);
        Class parseClass = responses.get(sid);
        dis.readShort();
        Map<Object, Object> result = ResponseUtil.parseColumns(parseClass, dis);
        dis.readByte();
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

    public static Map<Short, Class> getResponseParserMap() {
        return responses;
    }
}
