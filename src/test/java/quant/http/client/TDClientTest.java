package quant.http.client;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import quant.http.config.TDClientConfig;
import quant.http.requests.RequestBuilder;
import quant.http.requests.XMLRequestBuilder;
import quant.http.requests.builders.PriceHistoryBuilder;
import quant.http.requests.builders.QuoteBuilder;
import quant.xml.parser.responses.PriceHistory;

import javax.inject.Inject;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by dev on 12/22/14.
 */
@RunWith(Arquillian.class)
public class TDClientTest {
    @Deployment
    public static Archive<?> createDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml").
            importCompileAndRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "TDClientTest.war")
            .addPackages(true, "quant")
            .addAsLibraries(files)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    TDClient client;

    @Test
    public void quote () {
        XMLRequestBuilder xmlBuilder = client.XMLRequestBuilder();
        QuoteBuilder quoteBuilder = xmlBuilder.getQuoteBuilder();
        quoteBuilder.addSymbol("adxs")
                    .addSymbol("nwbo")
                    .addSymbol("aapl");
        try {
            Future<Map> result = client.execute(quoteBuilder.build());
            Map mapResults = result.get();
            System.out.println(mapResults);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void price_history() {
        XMLRequestBuilder xmlBuilder = client.XMLRequestBuilder();
        PriceHistoryBuilder priceHistory = xmlBuilder.getPriceHistoryBuilder();
        priceHistory.setRequestValue("ADXS, NWBO")
            .setIntervalType("MINUTE")
            .setIntervalDuration("1")
            .setExtended("true")
            .setStartDate("20141101")
            .setEndDate("20150101");
        try {
            Future<Map> result = client.execute(priceHistory.build());
            Map mapResults = result.get();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
