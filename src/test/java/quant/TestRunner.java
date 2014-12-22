package quant;

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
import quant.http.client.TDClient;
import quant.http.config.TDClientConfig;
import quant.http.requests.RequestBuilder;

import javax.inject.Inject;
import java.io.File;

/**
 * Created by dev on 12/21/14.
 */
@RunWith(Arquillian.class)
public class TestRunner {
    @Deployment
    public static Archive<?> createDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml").
            importRuntimeDependencies().resolve().withoutTransitivity().asFile();

        WebArchive war = ShrinkWrap.create(WebArchive.class, "TestME.war")
            .addClasses(TDClientConfig.class, TDClient.class, RequestBuilder.class)
            .addAsLibraries(files)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

        return war;
    }

    @Inject
    TDClientConfig config;

    @Test
    public void config_retrieve_env_vars () {
        Assert.fail("Not implemented with test values");
    }
}
