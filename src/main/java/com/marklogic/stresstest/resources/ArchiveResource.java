package com.marklogic.stresstest.resources;

import com.marklogic.stresstest.util.TestManager;
import com.sun.jersey.api.view.Viewable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 19/08/15
 * Time: 14:21
 * To change this template use File | Settings | File Templates.
 */
@Path("/archive")
public class ArchiveResource extends BaseResource {

    private static final Logger LOG = LoggerFactory.getLogger(ArchiveResource.class);

    // data model for freemarker .ftl template
    private Map<String, Object> createModel(String id) {
        Map<String, Object> map = createModel();
        map.put("title", "Archived Tests");
        map.put("files", TestManager.getSaveDirectoryFiles());
        map.put("filenames", TestManager.getSaveDirectoryListing());
        //map.put("metrics", TestManager.getStressTestInstance());
        //map.put("chart", formatForChart(TestManager.getStressTestInstance().getHostTimings()));
        return map;
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    public Viewable doPost() {
        return getArchive();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable getArchive() {
        //LOG.debug("Getting Dashboard ...");
        // LOG.debug(formatForChart(TestManager.getStressTestInstance().getHostTimings()));
        return new Viewable("/archive", createModel("Archived Tests"));
    }

    @GET
    @Path("load/{name}")
    @Produces(MediaType.TEXT_HTML)
    public Response getSavedTest(@PathParam("name") String name) {
        LOG.debug("Loading Saved test: " + name);
        TestManager.loadSessionData(name);

        URI uri = UriBuilder.fromPath("/").build();
        return Response.seeOther(uri).build();
    }

    @GET
    @Path("delete/{name}")
    @Produces(MediaType.TEXT_HTML)
    public Viewable deleteSavedTest(@PathParam("name") String name) {
        LOG.debug("Deleting Saved test: " + name);
        TestManager.deleteSavedSessionData(name);

        return new Viewable("/archive", createModel("Archived Tests"));
    }
}
