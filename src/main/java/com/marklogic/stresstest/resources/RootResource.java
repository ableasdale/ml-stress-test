package com.marklogic.stresstest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.sun.jersey.api.view.Viewable;
import com.sun.jersey.api.view.Viewable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;


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
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Dashboard and Overview");
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
        return new Viewable("/dashboard", createModel("Dashboard"));
    }


}