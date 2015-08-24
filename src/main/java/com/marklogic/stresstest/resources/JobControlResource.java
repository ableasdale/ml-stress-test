package com.marklogic.stresstest.resources;

import com.marklogic.stresstest.providers.TestScheduler;
import com.marklogic.stresstest.util.TestHelper;
import com.sun.jersey.api.view.Viewable;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 24/08/15
 * Time: 21:18
 * To change this template use File | Settings | File Templates.
 */
@Path("/jobs")
public class JobControlResource {

    private static final Logger LOG = LoggerFactory.getLogger(JobControlResource.class);


    // data model for freemarker .ftl template
    private Map<String, Object> createModel(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Active Jobs");
        map.put("metrics", TestHelper.getStressTestInstance());



        //map.put("chartMap", lhm);
        return map;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable getDashboard() {
        LOG.debug("Getting Dashboard ...");
        /*
        for (String k : TestHelper.getStressTestInstance().getHostTimingMaps().keySet()) {
            LOG.debug(formatForChart(TestHelper.getStressTestInstance().getHostTimingMaps().get(k)));
        }    */
        return new Viewable("/jobs", createModel("Running Jobs"));
    }

    @GET
    @Path("stop/{key}")
    @Produces(MediaType.TEXT_HTML)
    public Response stopJob(@PathParam("key") String key) {
        LOG.info("Stopping: " + key);
        try {
            TestScheduler.getScheduler().deleteJob(JobKey.jobKey("ForceMerge"));
        } catch (SchedulerException e) {
            TestHelper.returnExceptionString(e);
        }

        URI uri = UriBuilder.fromPath("/jobs").build();
        return Response.seeOther(uri).build();
    }
    //
}
