package quant.xml.parser;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by dev on 12/29/14.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({InitTest.class})
public class ResponseParserTest {
    private static InputStream quoteFixture;

    @BeforeClass
    public static void init() throws NoSuchMethodException {
        ClassLoader classLoader = ResponseParserTest.class.getClassLoader();
        quoteFixture = classLoader.getResourceAsStream("fixtures/quote-fixture.xml");
        ResponseParser.init();
    }
}
