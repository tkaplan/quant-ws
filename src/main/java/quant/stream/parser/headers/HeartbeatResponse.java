package quant.stream.parser.headers;

import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by dev on 12/12/14.
 */
public class HeartbeatResponse {
    protected static final byte headerId = (byte)'H';
    protected byte subType;
    protected long timestamp;

    private static Logger log = Logger.getLogger(HeartbeatResponse.class);

    public static void init() {

    }

    public static void parse(DataInputStream dis) throws IOException {
        byte[] heartBeat = new byte[9];
        dis.readFully(heartBeat);
        log.debug("Heart beat");
    }
}
