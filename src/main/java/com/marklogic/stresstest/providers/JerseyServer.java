package com.marklogic.stresstest.providers;

import com.marklogic.stresstest.resources.BaseResource;
import com.marklogic.stresstest.util.Consts;
import com.marklogic.stresstest.util.TestHelper;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.freemarker.FreemarkerViewProcessor;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 17/08/15
 * Time: 18:44
 * To change this template use File | Settings | File Templates.
 */
public class JerseyServer extends Thread {

    public static final URI BASE_URI = getBaseURI();
    private static Logger LOG = LoggerFactory.getLogger(JerseyServer.class);

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://0.0.0.0/")
                .port(Consts.GRIZZLY_HTTP_PORT).build();
    }

    @Override
    public void run() {
        try {
            LOG.info("Starting HTTP Service - CTRL^C to stop the server");
            ResourceConfig rc = new PackagesResourceConfig(BaseResource.class.getPackage().getName());
            rc.getProperties().put(
                    FreemarkerViewProcessor.FREEMARKER_TEMPLATES_BASE_PATH,
                    "freemarker");
            rc.getFeatures().put(ResourceConfig.FEATURE_IMPLICIT_VIEWABLES, true);
            HttpServer server = GrizzlyServerFactory.createHttpServer(BASE_URI, rc);

            StaticHttpHandler staticHttpHandler = new StaticHttpHandler(Consts.STATIC_RESOURCE_DIRECTORY_ROOT);
            server.getServerConfiguration().addHttpHandler(staticHttpHandler, "/vendor");

            server.start();

            synchronized (this) {
                // CTRL-C to stop the server - is there a better way to manage this?
                while (true) {
                    this.wait();
                }
            }

        } catch (Exception e) {
            TestHelper.returnExceptionString(e);
        }
    }
}
