package com.marklogic.stresstest.jobs.attic;

import com.marklogic.stresstest.util.TestManager;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPDigestAuthFilter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by ableasdale on 14/01/2017.
 */
public class RestEndpointRequest implements Job {

    public static String description = "Makes a call to a MarkLogic ReST endpoint and measures the response time";
    private Logger LOG = LoggerFactory.getLogger(RestEndpointRequest.class);
    private String timingGroup = "MLReST";


    public void execute(JobExecutionContext context) throws JobExecutionException {

        Client client = Client.create();
        client.addFilter(new HTTPDigestAuthFilter("q", "q"));

        WebResource webResource = client
                .resource("http://localhost:8002/manage/v2/forests/");

        //WebTarget target = client.target("http://localhost:8002").path("/manage/v2/forests/");

        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        //String output = response.getEntity(String.class);
        TestManager.addResultToTimingMap(timingGroup, "localhost", "0.10");

        //LOG.info("Output from Server .... \n");
        //LOG.info(output);
    }
}
