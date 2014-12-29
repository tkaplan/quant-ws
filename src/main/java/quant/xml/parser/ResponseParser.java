package quant.xml.parser;

import org.reflections.Reflections;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import quant.xml.parser.annotations.BinaryData;
import quant.xml.parser.annotations.Node;
import quant.xml.parser.annotations.Root;
import quant.xml.parser.responses.types.Quotes;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by dev on 12/25/14.
 */
public class ResponseParser {

    // We have HashMap which does the following
    // Response -> (ResponseType, ResponseParseMap)
    // ResponseParseMap returns HashMap<String, Object>
    // ResponseParseMap(NodeName, ParseFunction)

    private static Map<String,Map> responseTypeMap;
    private static Map<String, Object> responseParseMap;

    /**
     * This method caches and creates our response type
     * maps as well as our binary parse map.
     * @throws NoSuchMethodException
     */
    public static void init() throws NoSuchMethodException {
        // Build ResponseTypeMap
        responseTypeMap = new HashMap<>();

        // First response parse will be slow but then we will cache
        // our hashmap object
        Map<String, Object> mapParser = new HashMap<>();
        Reflections reflections = new Reflections("quant.xml.parser.responses.types");
        Set<Class<?>> subTypes = reflections.getSubTypesOf(Object.class);
        Iterator it = subTypes.iterator();
        while(it.hasNext()) {
            Class clazz = (Class) it.next();
            Annotation[] annotations = Quotes.class.getDeclaredAnnotations();
            for(Annotation annotation : annotations) {
                Class annClazz = annotation.annotationType();
                if(annClazz.equals(Root.class)) {
                    responseTypeMap.put(
                        clazz.getSimpleName(),
                        buildRootMap(clazz)
                    );
                } else if(annClazz.equals(BinaryData.class)) {
                    responseTypeMap.put(
                        clazz.getSimpleName(),
                        buildBinaryMap(clazz)
                    );
                }
            }
        }
    }

    /**
     * This class provides a list of standard parsing methods used to extract or
     * unmarshal our data into hashmap entities
     */
    private static class RootMethodMap {
        public static String StringType(org.w3c.dom.Node node, Map<String, Object> map) {
            return node.getTextContent();
        }
        public static Integer IntegerType(org.w3c.dom.Node node, Map<String, Object> map) {
            return Integer.parseInt(node.getTextContent());
        }
        public static Boolean BooleanType(org.w3c.dom.Node node, Map<String, Object> map) {
            return Boolean.parseBoolean(node.getTextContent());
        }
        public static Float FloatType(org.w3c.dom.Node node, Map<String, Object> map) {
            return Float.parseFloat(node.getTextContent());
        }
        public static Double DoubleType(org.w3c.dom.Node node, Map<String, Object> map) {
            return Double.parseDouble(node.getTextContent());
        }
        public static Map<String, Object> RootType(org.w3c.dom.Node node, Map<String, Object> map) throws InvocationTargetException, IllegalAccessException {
            return rootParse(node, (Map<String, Object>) map.get(node.getNodeName() + "-nested"));
        }
    }

    /**
     * IS is the input stream of our xml response object entity.getContent().
     * parseType is the parseMap that we need to use to build our entity
     * hashmap.
     *
     * @param is
     * @param parseType
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private static Map<String, Object> parse(InputStream is, String parseType) throws ParserConfigurationException, IOException, SAXException, InvocationTargetException, IllegalAccessException {
        DocumentBuilder builder = DocumentBuilderFactory
            .newInstance()
            .newDocumentBuilder();
        Document doc = builder.parse(is);
        return rootParse(doc, responseTypeMap.get(parseType));
    }

    /**
     * This method uses our method map to parse the correct values
     * and add them to the hash map. If we map all values we should
     * have a theoretical O of O(1). Though in practice this might
     * be O(1) >= actual <= O(N... if we don't map anything).
     *
     * @param parent
     * @param map
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private static Map<String, Object> rootParse(org.w3c.dom.Node parent, Map<String, Object> map) throws InvocationTargetException, IllegalAccessException {
        NodeList nodes = parent.getChildNodes();
        Map<String, Object> entity = new HashMap<>();
        int length = nodes.getLength();
        for(int i = 0; i < length; i ++) {
            org.w3c.dom.Node node = nodes.item(i);
            // Get mapping of this node to our root hashmap
            if(map.containsKey(node.getNodeName())) {
                // This will give us the method to invoke
                Method method = (Method)map.get(node.getNodeName());
                entity.put(node.getLocalName(), method.invoke(null, node, map));
            }
        }
        return entity;
    }

    /**
     * This gives us the correct method to add to our hash map
     *
     * @param type
     * @return
     * @throws NoSuchMethodException
     */
    private static Method parseMethod(Class type) throws NoSuchMethodException {
        if(type.equals(String.class)) {
            return RootMethodMap.class.getDeclaredMethod("StringType");
        } else if (type.equals(Integer.class)) {
            return RootMethodMap.class.getDeclaredMethod("IntegerType");
        }   else if (type.equals(Boolean.class)) {
            return RootMethodMap.class.getDeclaredMethod("BooleanType");
        }   else if (type.equals(Float.class)) {
            return RootMethodMap.class.getDeclaredMethod("FloatType");
        }   else if (type.equals(Double.class)) {
            return RootMethodMap.class.getDeclaredMethod("DoubleType");
        }

        return RootMethodMap.class.getDeclaredMethod("RootType");
    }

    /**
     * This builds the FunctionMap to use for our class
     *
     * @param clazz
     * @return
     * @throws NoSuchMethodException
     */
    public static Map<String,Object> buildRootMap(Class clazz) throws NoSuchMethodException {
        Map<String,Object> rootMap = new HashMap<>();
        Root root = (Root) clazz.getDeclaredAnnotation(Root.class);
        rootMap.put("name", root.name());
        Node[] nodes = root.nodes();
        for(Node node : nodes) {
            Method method = parseMethod(node.type());
            rootMap.put(node.name(),method);
            // If we get a root type method, we must add an embedded hashmap
            if(method.equals(RootMethodMap.class.getDeclaredMethod("RootType"))) {
                rootMap.put(node.name() + "-nested", buildRootMap(node.type()));
            }
        }
        return rootMap;
    }

    public static Map<String,Object> buildBinaryMap(Class clazz) {
        Map<String,Object> binaryMap = new HashMap<>();
        BinaryData bin = (BinaryData) clazz.getDeclaredAnnotation(BinaryData.class);
        binaryMap.put("name", bin.name());
        return new HashMap<>();
    }
}
