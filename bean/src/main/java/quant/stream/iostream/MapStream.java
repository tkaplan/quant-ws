package quant.stream.iostream;

import org.reflections.Reflections;
import quant.stream.parser.StreamParser;
import quant.stream.parser.headers.HeartbeatResponse;
import quant.stream.parser.headers.SnapshotResponse;
import quant.stream.parser.headers.StreamingResponse;

import javax.enterprise.concurrent.ManagedExecutorService;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by dev on 1/2/15.
 */
public class MapStream {
    private volatile DataInputStream dis;
    private volatile boolean interrupt = false;
    private volatile Map<Class, ArrayList<MapObserver>> observers;
    private volatile AtomicBoolean atomicBoolean;

    private ManagedExecutorService executor;

    public MapStream (InputStream is) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, NamingException {
        atomicBoolean = new AtomicBoolean();

        executor =  InitialContext.doLookup("java:comp/DefaultManagedExecutorService");
        dis = new DataInputStream(is);
        observers = new HashMap();

        // Reflections doesn't have a .getAllTypes method that returns Classes
        Reflections reflections = new Reflections("quant.stream.parser.headers.responses");
        Set<Class<? extends StreamingResponse>> streamClasses = reflections.getSubTypesOf(StreamingResponse.class);
        Set<Class<? extends SnapshotResponse>> snapshotClasses = reflections.getSubTypesOf(SnapshotResponse.class);
        Set<Class> allClasses = new HashSet<>();
        allClasses.addAll(streamClasses);
        allClasses.addAll(snapshotClasses);

        // Create linked list for concurrent hash map
        // Grab our observer using class key
        Iterator<Class> it = allClasses.iterator();
        while(it.hasNext()) {
            observers.put(it.next(), new ArrayList<>());
        }
    }

    /**
     * Clears a particular class of linked list observers
     * @param clazz
     */
    public synchronized void clear(Class clazz) {
        observers.get(clazz).clear();
    }

    /**
     * Clears all observers
     */
    public synchronized void clearAll() {
        Set<Class> classSet = observers.keySet();
        Iterator<Class> it = classSet.iterator();
        while(it.hasNext()) {
            observers.get(it.next()).clear();
        }
    }

    /**
     * One start per map stream instance.
     */
    public void start() throws Exception {
        if(atomicBoolean.getAndSet(true))
            return;
        while (!interrupt) {
            Map result = StreamParser.parse(dis);
            // We can just ignore heartbeat response
            if(result.get("ParseID").equals(HeartbeatResponse.class))
                continue;
            // Get a list of all observers for this class type
            ArrayList<MapObserver> observerList = observers.get(result.get("ParseID"));
            // Iterate through the list submit their tasks
            Iterator<MapObserver> it = observerList.iterator();
            while(it.hasNext()) {
                executor.submit(it.next().onData(result));
            }
        }
    }

    /**
     * Helper method to stop map streaming if we don't want to
     * close via streamparser interruption.
     */
    public synchronized void stop() {
        interrupt = true;
    }

    public synchronized void registerMapObserver(MapObserver mo) {
        observers.get(mo.getParseID()).add(mo);
    }

    public synchronized void unregisterMapObserver(MapObserver mo) {
        observers.get(mo.getParseID()).remove(mo);
    }

    public synchronized void clearMapObserversForClass(Class clazz) {
        if(observers.containsKey(clazz))
            observers.get(clazz).clear();
    }

    public synchronized void clearAllMapObservers() {
        Set<Class> classes = observers.keySet();
        Iterator<Class> it = classes.iterator();
        while(it.hasNext())
            observers.get(it.next()).clear();
    }
}
