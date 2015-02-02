package quant.stream.parser;

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
import org.reflections.Reflections;
import quant.http.client.TDClient;
import quant.stream.manager.StatusHolder;
import quant.stream.manager.Status;
import quant.stream.parser.response.MapObserverTest;

import javax.inject.Inject;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by dev on 1/1/15.
 */
@RunWith(Arquillian.class)
public class StreamParserTest {
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
    public void testResponses() throws Exception {
        Reflections reflections = new Reflections("quant.stream.parser.response");
        Set<Class<?>> subTypes = reflections.getTypesAnnotatedWith(ResponseTest.class);
        Iterator it = subTypes.iterator();

        // Begin stream so we can start our tests
        client.startStreaming();

        try {
            while(it.hasNext())
                runResponseTest((Class<MapObserverTest>) it.next());
        } finally {
            // After all is said and done
            client.stopStream();
        }
    }

    // Alot only 10 seconds per test
    public void runResponseTest(Class<MapObserverTest> moClass) throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        final CountDownLatch assertLatch = new CountDownLatch(1);
        Constructor<MapObserverTest> construct = moClass.getConstructor(CountDownLatch.class, CountDownLatch.class);
        MapObserverTest obs = construct.newInstance(latch, assertLatch);
        client.registerMapObserver(obs);
        StatusHolder statusHolder = client.updateStreaming(obs.request());
        if(latch.await(10, TimeUnit.SECONDS)) {
            Assert.assertEquals(1L, assertLatch.getCount());
            Assert.assertEquals(Status.OK, statusHolder.get());
            return;
        }
        Assert.fail();
    }
}
