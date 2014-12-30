package quant.xml.parser;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by dev on 12/29/14.
 */
public class InitTest {
    @Test
    public void initTest() throws
        InvocationTargetException,
        ParserConfigurationException,
        IllegalAccessException,
        SAXException,
        IOException,
        NoSuchFieldException
    {
        Field responseTypeMapField = ResponseParser.class.getDeclaredField("responseTypeMap");
        responseTypeMapField.setAccessible(true);
        Map<String,Object> responseTypeMap = (Map<String, Object>) responseTypeMapField.get(null);
        // Check if we have all the correct responseTypeMaps
        Assert.assertTrue(responseTypeMap.containsKey("OptionChain"));
        Assert.assertTrue(responseTypeMap.containsKey("VolatilityHistory"));
        Assert.assertTrue(responseTypeMap.containsKey("Quotes"));
        Assert.assertTrue(responseTypeMap.containsKey("PriceHistory"));
        Assert.assertTrue(responseTypeMap.containsKey("SymbolLookup"));
        responseTypeMapField.setAccessible(false);
    }

    @Test
    public void optionChainMap() throws IllegalAccessException, NoSuchFieldException {
        Field responseTypeMapField = ResponseParser.class.getDeclaredField("responseTypeMap");
        responseTypeMapField.setAccessible(true);
        Map<String,Object> responseTypeMap = (Map<String,Object>) responseTypeMapField.get(null);

        // Test option-chain
        Map<String,Object> responseType = (Map<String,Object>)responseTypeMap.get("OptionChain");
        Assert.assertTrue(responseType.containsKey("name"));
        Assert.assertEquals(responseType.get("name"),"amtd");
        Assert.assertTrue(responseType.containsKey("option-chain-results"));
        Method method = (Method)responseType.get("option-chain-results");
        Assert.assertEquals(method.getName(),"RootType");
        Assert.assertTrue(responseType.containsKey("option-chain-results-nested"));

        // Test option-chain-results
        responseType = (Map)responseType.get("option-chain-results-nested");
        Assert.assertEquals(((Method) responseType.get("symbol")).getName(), "StringType");
        Assert.assertEquals( ((Method)responseType.get("description")).getName(), "StringType");
        Assert.assertEquals( ((Method)responseType.get("bid")).getName(), "DoubleType");
        Assert.assertEquals( ((Method)responseType.get("ask")).getName(), "DoubleType");
        Assert.assertEquals( ((Method)responseType.get("bid-ask-size")).getName(), "StringType");
        Assert.assertEquals( ((Method)responseType.get("last")).getName(), "DoubleType");
        Assert.assertEquals( ((Method)responseType.get("open")).getName(), "DoubleType");
        Assert.assertEquals( ((Method)responseType.get("high")).getName(), "DoubleType");
        Assert.assertEquals( ((Method)responseType.get("low")).getName(), "DoubleType");
        Assert.assertEquals( ((Method)responseType.get("close")).getName(), "DoubleType");
        Assert.assertEquals( ((Method)responseType.get("volume")).getName(), "IntegerType");
        Assert.assertEquals( ((Method)responseType.get("change")).getName(), "DoubleType");
        Assert.assertEquals( ((Method)responseType.get("quote-punctuality")).getName(), "StringType");
        Assert.assertEquals( ((Method)responseType.get("option-date")).getName(), "RootType");

        // Test option-date
        responseType = (Map)responseType.get("option-date-nested");
        Assert.assertEquals( ((Method)responseType.get("date")).getName(), "StringType");
        Assert.assertEquals( ((Method)responseType.get("expiration-type")).getName(), "StringType");
        Assert.assertEquals( ((Method)responseType.get("days-to-expiration")).getName(), "IntegerType");
        Assert.assertEquals( ((Method)responseType.get("option-strike")).getName(), "RootType");

        // Test strike-price
        responseType = (Map)responseType.get("option-strike-nested");
        Assert.assertEquals( ((Method)responseType.get("strike-price")).getName(), "DoubleType");
        Assert.assertEquals( ((Method)responseType.get("standard-option")).getName(), "BooleanType");
        Assert.assertEquals( ((Method)responseType.get("put")).getName(), "RootType");
        Assert.assertEquals( ((Method)responseType.get("call")).getName(), "RootType");

        Map put = (Map)responseType.get("put-nested");
        Map call = (Map)responseType.get("call-nested");

        // Test put and call
        Assert.assertEquals( ((Method)put.get("time-value-index")).getName(), "DoubleType");
        Assert.assertEquals( ((Method)put.get("multiplier")).getName(), "IntegerType");
        Assert.assertEquals( ((Method)put.get("change")).getName(), "DoubleType");
        Assert.assertEquals( ((Method)put.get("change-percentage")).getName(), "StringType");
        Assert.assertEquals( ((Method)put.get("in-the-money")).getName(), "BooleanType");
        Assert.assertEquals( ((Method)put.get("near-the-money")).getName(), "BooleanType");
        Assert.assertEquals( ((Method)put.get("theoretical-value")).getName(), "DoubleType");
        Assert.assertEquals( ((Method)put.get("deliverable-list")).getName(), "RootType");

        Assert.assertEquals( ((Method)call.get("time-value-index")).getName(), "DoubleType");
        Assert.assertEquals( ((Method)call.get("multiplier")).getName(), "IntegerType");
        Assert.assertEquals( ((Method)call.get("change")).getName(), "DoubleType");
        Assert.assertEquals( ((Method)call.get("change-percentage")).getName(), "StringType");
        Assert.assertEquals( ((Method)call.get("in-the-money")).getName(), "BooleanType");
        Assert.assertEquals( ((Method)call.get("near-the-money")).getName(), "BooleanType");
        Assert.assertEquals( ((Method)call.get("theoretical-value")).getName(), "DoubleType");
        Assert.assertEquals( ((Method)call.get("deliverable-list")).getName(), "RootType");
        Assert.assertEquals(28,call.size());

        // Test deliverable-list
        Map deliverableList = (Map)put.get("deliverable-list-nested");
        Assert.assertEquals( ((Method)deliverableList.get("row")).getName(), "RootType");
        Map row = (Map)deliverableList.get("row-nested");
        Assert.assertEquals(((Method) row.get("shares")).getName(),"IntegerType");
        Assert.assertEquals(3,row.size());
    }
}
