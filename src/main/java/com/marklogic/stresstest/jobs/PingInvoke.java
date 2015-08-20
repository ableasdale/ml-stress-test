package com.marklogic.stresstest.jobs;

import com.marklogic.stresstest.helpers.TestHelper;
import com.marklogic.stresstest.providers.LoadBalancedMarkLogicContentSource;
import com.marklogic.stresstest.providers.XQueryModules;
import com.marklogic.xcc.ContentSource;
import com.marklogic.xcc.Request;
import com.marklogic.xcc.ResultSequence;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created by jjames on 8/18/15.
 */
public class PingInvoke implements Job {
    private Logger LOG = LoggerFactory.getLogger(PingInvoke.class);
    private String timingGroup = "groupA";

    public void execute(JobExecutionContext context) throws JobExecutionException {
        //Session s = LoadBalancedMarkLogicContentSource.getInstance().openSession();
        //Request request = s.newModuleInvoke("ping-invoke.xqy");
        // there are no args to pass in on this one


        for (ContentSource cs : LoadBalancedMarkLogicContentSource.getInstance().getCopyOfActiveContentSourceList()) {
            Session s = cs.newSession();
            try {
                //LOG.debug("PingGroupA: Range query for recent # docs ...");
                Request request = s.newModuleInvoke("ping-invoke.xqy");
                ResultSequence rs = s.submitRequest(request);
                //ResultSequence rs = s.submitRequest(s.newAdhocQuery(XQueryModules.getInstance().pingMarkLogic()));
                String[] results = rs.asStrings();
                List<String> timingsList = TestHelper.getStressTestInstance().getHostTimingMaps().get(timingGroup).get(s.getConnectionUri().getHost());
                timingsList.add(results[1].substring(2, results[1].length() - 1));
                TestHelper.getStressTestInstance().getHostTimingMaps().get(timingGroup).put(s.getConnectionUri().getHost(), timingsList);
                LOG.debug(String.format("PingGroupA - total documents: %s Execution time: %s", results[0], results[1]));
                s.close();
            } catch (RequestException e) {
                LOG.error(TestHelper.returnExceptionString(e));
            }
        }


        /*
        try {
            //LOG.debug("PingGroupA: Range query for recent # docs ...");
            ResultSequence rs = s.submitRequest(request);
            String[] results = rs.asStrings();
            LOG.info(String.format("PingInvoke - total documents: %s Execution time: %s", results[0], results[1]));
            s.close();
        } catch (RequestException e) {
            LOG.error(TestHelper.returnExceptionString(e));
        } */
    }
}
