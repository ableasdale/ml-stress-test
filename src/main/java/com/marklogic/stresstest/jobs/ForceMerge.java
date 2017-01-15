package com.marklogic.stresstest.jobs;

import com.marklogic.stresstest.providers.LoadBalancedMarkLogicContentSource;
import com.marklogic.stresstest.util.TestManager;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: alexb
 * Date: 15/08/15
 * Time: 10:39
 * To change this template use File | Settings | File Templates.
 */
public class ForceMerge implements Job {
    private Logger LOG = LoggerFactory.getLogger(ForceMerge.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {

        Session s = LoadBalancedMarkLogicContentSource.getInstance().openSession();
        try {
            LOG.debug("Forcing a Merge...");
            s.submitRequest(s.newAdhocQuery("xdmp:merge()"));
            s.close();
        } catch (RequestException e) {
            LOG.error(TestManager.returnExceptionString(e));
        }
    }

}

