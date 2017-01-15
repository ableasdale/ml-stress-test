package com.marklogic.stresstest.jobs;

import com.marklogic.stresstest.beans.JobSpec;
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
public class HTTPEndpointGetRequest implements Job {

    public static String description = "Makes a call to the MarkLogic Admin API and measures the response time";
    private Logger LOG = LoggerFactory.getLogger(HTTPEndpointGetRequest.class);
    private String timingGroup = "MLAdmin";

    /*
    public HTTPEndpointGetRequest(JobSpec j){
        LOG.info("Hit the CONSTRUCTOR!");
        LOG.info(j.getEndpoint());
    } */


    public void execute(JobExecutionContext context) throws JobExecutionException {
        Client client = Client.create();
        client.addFilter(new HTTPDigestAuthFilter("q", "q"));

        // TODO - Resource URI is hard coded!
        WebResource webResource = client
                .resource("http://localhost:8001");

        long startTime = System.currentTimeMillis();

        ClientResponse response = webResource.accept("text/html")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException(String.format("Failed : HTTP error code : %d", response.getStatus()));
        }

        long elapsedTime = System.currentTimeMillis() - startTime;

        // TODO - "localhost" value is hardcoded - need to figure out how we're going to deal with this in future (was put in for XCC)
        TestManager.addResultToTimingMap(timingGroup, "localhost", String.format("%.2f", elapsedTime / 1000.0f));

    }
}
