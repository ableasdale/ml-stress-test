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

    public void execute(JobExecutionContext context) throws JobExecutionException {

        LOG.debug(String.format("Job fired at: %s", context.getFireTime()));

        JobSpec js = (JobSpec) context.getJobDetail().getJobDataMap().get("jobSpec");

        Client client = Client.create();

        client.addFilter(new HTTPDigestAuthFilter(js.getUsername(), js.getPassword()));

        WebResource webResource = client.resource(js.getEndpoint());

        long startTime = System.currentTimeMillis();

        ClientResponse response = webResource.accept(js.getAccepts()).get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException(String.format("Failed : HTTP error code : %d", response.getStatus()));
        }

        long elapsedTime = System.currentTimeMillis() - startTime;
        LOG.debug(String.format("Endpoint response:\n%s", response.getEntity(String.class)));

        // TODO - "localhost" value is hardcoded - need to figure out how we're going to deal with this in future (was put in for XCC)
        TestManager.addResultToTimingMap(js.getGroupname(), "localhost", String.format("%.2f", elapsedTime / 1000.0f));

    }
}
