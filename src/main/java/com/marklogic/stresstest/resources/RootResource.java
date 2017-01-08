package com.marklogic.stresstest.resources;

import com.marklogic.stresstest.util.TestHelper;
import com.sun.jersey.api.view.Viewable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 17/08/15
 * Time: 13:30
 * To change this template use File | Settings | File Templates.
 */

@Path("/")
public class RootResource extends BaseResource {

    private static final Logger LOG = LoggerFactory.getLogger(RootResource.class);

    // data model for freemarker .ftl template
    private Map<String, Object> createModel(String id) {

        Map map = createModel();
        map.put("title", "Dashboard and Overview");
        map.put("metrics", TestHelper.getStressTestInstance());

        Map<String, String> lhm = new LinkedHashMap<String, String>();
        for (String k : TestHelper.getStressTestInstance().getHostTimingMaps().keySet()) {
            lhm.put(k, formatForChart(TestHelper.getStressTestInstance().getHostTimingMaps().get(k)));
        }
        map.put("chartMap", lhm);
        return map;
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    public Viewable doPost() {
        return getDashboard();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable getDashboard() {
        LOG.debug("Getting Dashboard ...");
        for (String k : TestHelper.getStressTestInstance().getHostTimingMaps().keySet()) {
            LOG.debug(formatForChart(TestHelper.getStressTestInstance().getHostTimingMaps().get(k)));
        }
        return new Viewable("/dashboard", createModel("Dashboard"));
    }

}