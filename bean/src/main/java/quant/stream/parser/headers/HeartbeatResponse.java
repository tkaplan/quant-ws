package quant.stream.parser.headers;

import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dev on 12/12/14.
 */
public class HeartbeatResponse {
    protected static final byte headerId = (byte)'H';
    protected byte subType;
    protected long timestamp;

    public static void init() {

    }

    public static Map parse(DataInputStream dis) throws IOException {
        byte[] heartBeat = new byte[9];
        dis.readFully(heartBeat);
        Map<String,Object> res = new HashMap<>();
        res.put("Heartbeat",true);
        res.put("ParseID",HeartbeatResponse.class);
        return res;
    }
}
