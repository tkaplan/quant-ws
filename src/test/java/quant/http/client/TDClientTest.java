package quant.http.client;

import org.apache.http.client.methods.HttpGet;
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

import javax.inject.Inject;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 * Created by dev on 12/22/14.
 */
@RunWith(Arquillian.class)
public class TDClientTest {
    @Deployment
    public static Archive<?> createDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml").
            importRuntimeDependencies().resolve().withoutTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "TDClientTest.war")
            .addPackages(true, "quant")
            .addAsLibraries(files)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    TDClient client;

    @Test
    public void client () {
        //Assert.fail("Not implemented");
        XMLRequestBuilder xmlBuilder = client.XMLRequestBuilder();
        PriceHistoryBuilder priceHistory = xmlBuilder.getPriceHistoryBuilder();
        priceHistory.setRequestValue("adxs, nwbo");
        priceHistory.setIntervalType("MINUTE");
        priceHistory.setIntervalDuration("1");
        priceHistory.setStartDate("20141101");
        priceHistory.setExtended("false");
        try {
            client.execute(priceHistory.build());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
