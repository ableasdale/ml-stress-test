package com.marklogic.stresstest.jobs;

import com.marklogic.stresstest.beans.JobSpec;
import com.marklogic.stresstest.providers.LoadBalancedMarkLogicContentSource;
import com.marklogic.stresstest.providers.XQueryModules;
import com.marklogic.stresstest.util.TestManager;
import com.marklogic.xcc.ContentSource;
import com.marklogic.xcc.ResultSequence;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ableasdale on 16/01/2017.
 */
public class XCCModuleInvoker implements Job {

    private Logger LOG = LoggerFactory.getLogger(XCCModuleInvoker.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobSpec js = (JobSpec) context.getJobDetail().getJobDataMap().get("jobSpec");

        for (ContentSource cs : LoadBalancedMarkLogicContentSource.getInstance().getCopyOfActiveContentSourceList()) {
            Session s = cs.newSession();
            try {
                //LOG.debug("PingGroupA: Range query for recent # docs ...");
                //LOG.info("XCC status: "+s.getCurrentServerPointInTime());
                ResultSequence rs = s.submitRequest(s.newAdhocQuery(XQueryModules.getInstance().adhocQuery(js.getEndpoint())));
                String[] results = rs.asStrings();

                LOG.debug("XCC Result Set Size "+results.length);
                //LOG.info(js.getGroupname());
                TestManager.addResultToTimingMap(js.getGroupname(), s.getConnectionUri().getHost(), results[results.length - 1].substring(2, results[results.length - 1].length() - 1));

                s.close();
            } catch (RequestException e) {
                LOG.error(TestManager.returnExceptionString(e));
            }
        }
    }
}
