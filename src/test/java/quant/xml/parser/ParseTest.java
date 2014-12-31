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
import java.util.Set;

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
        Assert.assertEquals((String)dell.get("symbol"),"DELL");
        Assert.assertEquals((Integer)dell.get("volume"), new Integer(2028822));

        Map fffex = (Map) quotesList.get(1);
        Assert.assertEquals((String)fffex.get("symbol"),"FFFEX");
        Assert.assertEquals((Double)fffex.get("nav"), new Double(16.75));

        Map amtd = (Map) quotesList.get(2);
        Assert.assertEquals((String)amtd.get("symbol"),"AMTD_20070922P12.5");
        Assert.assertEquals((Double)amtd.get("implied-volatility"), new Double(68.074));

        Map spx = (Map) quotesList.get(3);
        Assert.assertEquals((String)spx.get("symbol"),"$SPX.X");
        Assert.assertEquals((String)spx.get("asset-type"), "I");
    }

    @Test
    public void priceHistoryTest() throws InvocationTargetException, ParserConfigurationException, IllegalAccessException, SAXException, IOException {
        InputStream is = ResponseParserTest.getPriceHistoryFixture();
        Map priceHistory = ResponseParser.parse(is,"PriceHistory");
        List quotes = (List) ((Map)priceHistory.get("AMTD")).get("quotes");
        Map quote1 = (Map) quotes.get(0);
        Map quote2 = (Map) quotes.get(1);
        Assert.assertEquals((Float)quote1.get("volume"), new Float(36806.08));
        Assert.assertEquals((Float)quote2.get("volume"), new Float(29463.25));
    }
}
