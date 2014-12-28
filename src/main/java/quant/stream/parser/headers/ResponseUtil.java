package quant.stream.parser.headers;

import quant.stream.parser.annotations.Column;
import quant.stream.parser.annotations.RepeatableColumns;
import org.apache.log4j.Logger;
import org.reflections.Reflections;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.zip.InflaterInputStream;

/**
 * Created by dev on 12/14/14.
 */
public class ResponseUtil {
    private static Map<Class, Method> parseMap;

    /**
     * Class ie: {SCharts.class, SLevel2OPNY.class}
     *  - Column ie: 0x80, 0x81
     *      - Map {field, type} -> value
     */
    private static Map<Class, Map<Short,Map<String,Object>>> columnMap;

    private static Logger log = Logger.getLogger(ResponseUtil.class);

    private static ByteBuffer uncompress(byte[] data) throws IOException {
        // Now that we have our data lets
        // inflate it with gzip
        byte[] uncompress = new byte[255];
        ByteArrayInputStream bins = new ByteArrayInputStream(data);
        InflaterInputStream inflate = new InflaterInputStream(bins);
        DataInputStream reader = new DataInputStream(inflate);
        int i = 0;
        while(reader.available() > 0) {
            reader.read(uncompress, i, 1);
            i ++;
        }
        return ByteBuffer.wrap(uncompress);
    }

    private static class FunctionMap {
        public static Short parseShort(DataInputStream dis) throws IOException {
            return dis.readShort();
        }

        public static Float parseFloat(DataInputStream dis) throws IOException {
            return dis.readFloat();
        }

        public static Integer parseInteger(DataInputStream dis) throws IOException {
            return dis.readInt();
        }

        public static Character parseCharacter(DataInputStream dis) throws IOException {
            return dis.readChar();
        }

        public static String parseString(DataInputStream dis) throws IOException {
            Short size = dis.readShort();
            byte[] data = new byte[size];
            dis.readFully(data);
            return new String(data);
        }

        public static ByteBuffer parseByteBuffer(DataInputStream dis) throws IOException {
            Stack<Byte> buffer = new Stack<>();
            Short size = dis.readShort();
            byte[] data = new byte[size];
            dis.readFully(data);
            return uncompress(data);
        }
    }

    // Works
    public static void initializeParseMap() throws NoSuchMethodException {
        parseMap = new HashMap<>();
        parseMap.put(Short.class, FunctionMap.class.getDeclaredMethod("parseShort", DataInputStream.class));
        parseMap.put(Float.class, FunctionMap.class.getDeclaredMethod("parseFloat", DataInputStream.class));
        parseMap.put(Integer.class, FunctionMap.class.getDeclaredMethod("parseInteger", DataInputStream.class));
        parseMap.put(Character.class, FunctionMap.class.getDeclaredMethod("parseCharacter", DataInputStream.class));
        parseMap.put(String.class, FunctionMap.class.getDeclaredMethod("parseString", DataInputStream.class));
        parseMap.put(ByteBuffer.class, FunctionMap.class.getDeclaredMethod("parseByteBuffer", DataInputStream.class));
    }

    // This could be initialized concurrently
    // Works
    public static synchronized void initializeColumnMap(Set<Class> classes) throws NoSuchFieldException {
        if(columnMap == null) {
            columnMap = new HashMap<>();
        }
        // Now we iterate through our clases
        Iterator it = classes.iterator();
        while(it.hasNext()) {
            // Since we map a type map to our class we must instantiate
            // a new one each time.
            Map<Short, Map<String, Object>> typeMap = new HashMap<>();
            // Now we want to get our class
            Class clazz = (Class)it.next();
            // Get our annotation RepeatableColumns
            RepeatableColumns repeatableColumns = (RepeatableColumns)clazz.getAnnotation(RepeatableColumns.class);
            // Now we get our columns
            Column[] columns = repeatableColumns.value();
            // Iterate through each column get our type and index
            for(Column column : columns) {
                Map<String, Object> recordMap = new HashMap<>();
                recordMap.put("field", column.field());
                recordMap.put("type", column.type());
                // Add our type class to an index
                typeMap.put((short)column.index(),recordMap);
            }
            columnMap.put(clazz, typeMap);
        }
    }

    // Works
    public static Map<Short, Class> init(Class classType) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        // This is used for mapping are parser function to our column type
        initializeParseMap();
        Reflections reflections = new Reflections("dev.http.parser.headers.responses");
        Set<Class> subTypes = reflections.getSubTypesOf(classType);
        // We must now initialize our typeMap
        initializeColumnMap(subTypes);
        Map<Short, Class> responses = new HashMap<Short, Class>();
        Iterator it = subTypes.iterator();
        while(it.hasNext()) {
            Class responseClass = (Class)it.next();
            Field field = null;

            try {
                field = responseClass.getDeclaredField("sid");
            } catch(NoSuchFieldException e) {
                //Do nothing or log it
            }

            // If sid is null, it's possible we have multiple sids
            if(field == null) {
                field = responseClass.getDeclaredField("sids");
                field.setAccessible(true);
                Short[] sids = (Short[])field.get(null);
                if(sids == null) {
                    throw new Error("Response class must have an sid.");
                }
                // We now add an sid to class
                for(Short serviceId: sids) {
                    responses.put(serviceId, responseClass);
                }
                field.setAccessible(false);
            } else {
                field.setAccessible(true);
                Short sid = (Short)field.get(null);
                // We don't have multiple sids for parsing
                responses.put(sid, responseClass);
                field.setAccessible(false);
            }
        }

        return responses;
    }

    // Need to test
    public static Map<Object,Object> parseColumns(Class response, DataInputStream dis) throws IOException, InvocationTargetException, IllegalAccessException {
        /***
         * If the delimiters don't work, we can simply just
         * make a byte buffer that that reads in the length
         * of the message and then turn that into an input
         * stream.
         */
        Map<Object,Object> columns = new HashMap<Object,Object>();
        // Get our type map so we only have to do
        Map<Short, Map<String, Object>> typeMap = columnMap.get(response);
        Map<String, Object> recordMap;
        while(true) {
            // We need to convert unsigned byte to short
            short column = (short)dis.readUnsignedByte();
            // We could be getting one of the following
            // 1. Short
            // 2. Float
            // 3. Character
            // 4. String
            // 5. ByteBuffer
            if(!typeMap.containsKey(column) && column == (short)(0xFF)) {
                break;
            }
            recordMap = typeMap.get(column);
            Class type = (Class)recordMap.get("type");
            String field = (String)recordMap.get("field");
            // Get the correct type and parse our method
            Object object = parseMap.get(type).invoke(null, dis);
            // Now we want to save the object so we can retrieve it by
            // index or field
            columns.put(field, object);
            columns.put(column, object);
        }
        // Check if there is data to extract
        try {
            Method extract = response.getDeclaredMethod("extract",Map.class);
            columns = (Map<Object,Object>)extract.invoke(null,columns);
        } catch (NoSuchMethodException e) {
            // Do nothing if there is no extract method
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return columns;
    }
}
