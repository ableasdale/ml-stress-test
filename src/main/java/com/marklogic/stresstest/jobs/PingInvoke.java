package com.marklogic.stresstest.jobs;

import com.marklogic.stresstest.helpers.TestHelper;
import com.marklogic.stresstest.providers.SingleNodeMarkLogicContentSource;
import com.marklogic.stresstest.providers.XQueryModules;
import com.marklogic.xcc.ResultSequence;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.Request;
import com.marklogic.xcc.exceptions.RequestException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by jjames on 8/18/15.
 */
public class PingInvoke implements Job {
    private Logger LOG = LoggerFactory.getLogger(PingInvoke.class);

    public void execute(JobExecutionContext context) throws JobExecutionException{
        Session s = SingleNodeMarkLogicContentSource.getInstance().getSession();
        Request request = s.newModuleInvoke("ping-invoke.xqy");
        // there are no args to pass in on this one

        try {
            //LOG.debug("Ping: Range query for recent # docs ...");
            ResultSequence rs = s.submitRequest(request);
            String[] results = rs.asStrings();
            LOG.info(String.format("PingInvoke - total documents: %s Execution time: %s", results[0], results[1]));
            s.close();
        } catch (RequestException e) {
            LOG.error(TestHelper.returnExceptionString(e));
        }
    }
}
