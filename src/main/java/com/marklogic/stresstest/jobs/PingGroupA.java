package com.marklogic.stresstest.jobs;

/**
 * Created with IntelliJ IDEA.
 * User: alexb
 * Date: 15/08/15
 * Time: 10:02
 * To change this template use File | Settings | File Templates.
 */

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


public class PingGroupA implements Job {

    public static String description = "Routinely pings all nodes in a MarkLogic cluster";

    private Logger LOG = LoggerFactory.getLogger(PingGroupA.class);
    private String timingGroup = "groupA";

    public static String getDescription() {
        return description;
    }

    public static void setDescription(String description) {
        PingGroupA.description = description;
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {

        for (ContentSource cs : LoadBalancedMarkLogicContentSource.getInstance().getCopyOfActiveContentSourceList()) {
            Session s = cs.newSession();
            try {
                //LOG.debug("PingGroupA: Range query for recent # docs ...");
                ResultSequence rs = s.submitRequest(s.newAdhocQuery(XQueryModules.getInstance().pingMarkLogic()));
                String[] results = rs.asStrings();

                TestManager.addResultToTimingMap(timingGroup, s.getConnectionUri().getHost(), results[1].substring(2, results[1].length() - 1));

                LOG.debug(String.format("PingGroupA - total documents: %s Execution time: %s", results[0], results[1]));
                s.close();
            } catch (RequestException e) {
                LOG.error(TestManager.returnExceptionString(e));
            }
        }

        /*  Original version
        Session s = LoadBalancedMarkLogicContentSource.getInstance().openSession();
        try {
            //LOG.debug("PingGroupA: Range query for recent # docs ...");
            ResultSequence rs = s.submitRequest(s.newAdhocQuery(XQueryModules.getInstance().pingMarkLogic()));
            String[] results = rs.asStrings();
            String x = results[1].substring(2, results[1].length() - 1);
            List<String> timingsList = TestManager.getStressTestInstance().getHostTimings().get(s.getConnectionUri().getHost());
            timingsList.add(x);
            TestManager.getStressTestInstance().getHostTimings().put(s.getConnectionUri().getHost(), timingsList);

            LOG.debug(String.format("PingGroupA - total documents: %s Execution time: %s", results[0], results[1]));
            s.close();
        } catch (RequestException e) {
            LOG.error(TestManager.returnExceptionString(e));
        } */

    }
}