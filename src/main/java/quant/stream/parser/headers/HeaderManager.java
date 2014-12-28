package quant.stream.parser.headers;

import java.io.DataInputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dev on 12/13/14.
 */
public class HeaderManager {

    private static Map<Byte, Class<?>> frames;

    public static void init() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        frames = new HashMap<Byte, Class<?>>();
        frames.put((byte)'N', SnapshotResponse.class);
        frames.put((byte)'S', StreamingResponse.class);
        frames.put((byte)'H', HeartbeatResponse.class);

        HeartbeatResponse.init();
        SnapshotResponse.init();
        StreamingResponse.init();
    }

    public static void parse(byte header, DataInputStream dis) throws Exception {
        Class responseClass = frames.get(header);

        // Can we handle this exception?
        if(responseClass == null) {
            for(int i = 0; i < 200; i ++) {
                System.out.println(dis.read());
            }
            throw new Exception("I have no idea how to handle this header: " + header);
        }

        Method method = responseClass.getMethod("parse", DataInputStream.class);
        // Invoke our method parse
        method.invoke(null, dis);
    }
}
