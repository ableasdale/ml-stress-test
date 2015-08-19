package com.marklogic.stresstest.jobs;

/**
 * Created with IntelliJ IDEA.
 * User: alexb
 * Date: 15/08/15
 * Time: 10:02
 * To change this template use File | Settings | File Templates.
 */

import com.marklogic.stresstest.helpers.TestHelper;
import com.marklogic.stresstest.providers.LoadBalancedMarkLogicContentSource;
import com.marklogic.stresstest.providers.XQueryModules;
import com.marklogic.xcc.ResultSequence;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


public class Ping implements Job {
    private Logger LOG = LoggerFactory.getLogger(Ping.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {

        Session s = LoadBalancedMarkLogicContentSource.getInstance().openSession();
        try {
            //LOG.debug("Ping: Range query for recent # docs ...");
            ResultSequence rs = s.submitRequest(s.newAdhocQuery(XQueryModules.getInstance().pingMarkLogic()));
            String[] results = rs.asStrings();
            String x = results[1].substring(2, results[1].length() - 1);
            List timingsList = TestHelper.getStressTestInstance().getHostTimings().get(s.getConnectionUri().getHost());
            timingsList.add(x);
            TestHelper.getStressTestInstance().getHostTimings().put(s.getConnectionUri().getHost(), timingsList);
            // TODO - remove the timings list!
            TestHelper.timingsList.add(x);
            LOG.debug(String.format("Ping - total documents: %s Execution time: %s", results[0], results[1]));
            s.close();
        } catch (RequestException e) {
            LOG.error(TestHelper.returnExceptionString(e));
        }

        /* My original ping code - returns the timestamp
        try {
            LOG.info(String.format("Ping: MarkLogic Current Query Timestamp: %s", SingleNodeMarkLogicContentSource.getInstance().getSession().getCurrentServerPointInTime()));
        } catch (RequestException e) {
            LOG.error(TestHelper.returnExceptionString(e));
        } */

    }
}