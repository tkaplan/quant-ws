package quant.xml.parser;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by dev on 12/30/14.
 */
public class ParseTest {
    @Test
    public void quoteParseTest() throws InvocationTargetException, ParserConfigurationException, IllegalAccessException, SAXException, IOException {
        InputStream is = ResponseParserTest.getQuoteFixture();
        Map quotes = ResponseParser.parse(is,"Quotes");
        Map quoteList = (Map) ((List)quotes.get("quote-list")).get(0);
        List quotesList = (List)quoteList.get("quote");

        Map dell = (Map) quotesList.get(0);
        Assert.assertEquals((String)((List)dell.get("symbol")).get(0),"DELL");
        Assert.assertEquals((Integer) ((List) dell.get("volume")).get(0), new Integer(2028822));

        Map fffex = (Map) quotesList.get(1);
        Assert.assertEquals((String)((List)fffex.get("symbol")).get(0),"FFFEX");
        Assert.assertEquals((Double) ((List)fffex.get("nav")).get(0), new Double(16.75));

        Map amtd = (Map) quotesList.get(2);
        Assert.assertEquals((String)((List)amtd.get("symbol")).get(0),"AMTD_20070922P12.5");
        Assert.assertEquals((Double) ((List)amtd.get("implied-volatility")).get(0), new Double(68.074));

        Map spx = (Map) quotesList.get(3);
        Assert.assertEquals((String)((List)spx.get("symbol")).get(0),"$SPX.X");
        Assert.assertEquals((String) ((List)spx.get("asset-type")).get(0), "I");
    }
}
