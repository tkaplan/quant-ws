package quant.xml.parser;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.apache.tools.ant.filters.StringInputStream;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by dev on 12/29/14.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({InitTest.class,ParseTest.class})
public class ResponseParserTest {
    private static InputStream quoteFixture;
    private static ByteArrayInputStream priceHistoryFixture;

    @BeforeClass
    public static void init() throws NoSuchMethodException, IOException, DecoderException {
        ClassLoader classLoader = ResponseParserTest.class.getClassLoader();
        quoteFixture = classLoader.getResourceAsStream("fixtures/quote-fixture.xml");
        InputStream priceHistoryStream = classLoader.getResourceAsStream("fixtures/price-history.bin");
        String priceHistoryString = IOUtils.toString(priceHistoryStream, "UTF-8");
        byte[] bytes = Hex.decodeHex(priceHistoryString.toCharArray());
        priceHistoryFixture = new ByteArrayInputStream(bytes);
        ResponseParser.init();
    }

    public static InputStream getQuoteFixture() {
        return quoteFixture;
    }

    public static InputStream getPriceHistoryFixture() {
        return priceHistoryFixture;
    }
}
