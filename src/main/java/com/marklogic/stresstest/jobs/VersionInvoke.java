package com.marklogic.stresstest.jobs;

import com.marklogic.stresstest.providers.LoadBalancedMarkLogicContentSource;
import com.marklogic.stresstest.util.TestHelper;
import com.marklogic.xcc.ContentSource;
import com.marklogic.xcc.Request;
import com.marklogic.xcc.ResultSequence;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;
import com.marklogic.xcc.types.ValueType;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by jjames on 8/20/15.
 */
public class VersionInvoke implements Job {
    private Logger LOG = LoggerFactory.getLogger(VersionInvoke.class);
    private String timingGroup = "groupV";

    public void execute(JobExecutionContext context) throws JobExecutionException {
        //Session s = LoadBalancedMarkLogicContentSource.getInstance().openSession();
        //Request request = s.newModuleInvoke("ping-invoke.xqy");
        // there are no args to pass in on this one


        for (ContentSource cs : LoadBalancedMarkLogicContentSource.getInstance().getCopyOfActiveContentSourceList()) {
            Session s = cs.newSession();
            try {
                long startTime = System.currentTimeMillis();
                long endTime;
                //LOG.debug("Ping: Range query for recent # docs ...");
                Request request = s.newModuleInvoke("version.xqy");
                request.setNewVariable("format", ValueType.XS_STRING, "text");
                ResultSequence rs = s.submitRequest(request);
                endTime = System.currentTimeMillis();
                //ResultSequence rs = s.submitRequest(s.newAdhocQuery(XQueryModules.getInstance().pingMarkLogic()));
                String[] results = rs.asStrings();
                TestHelper.addResultToTimingMap(timingGroup, s.getConnectionUri().getHost(), String.format("%d", (endTime-startTime)));
                LOG.debug(String.format("Version - version %s, total Server execution time: %d", results[0], (endTime-startTime)));
                s.close();
            } catch (RequestException e) {
                LOG.error(TestHelper.returnExceptionString(e));
            }
        }


        /*
        try {
            //LOG.debug("Ping: Range query for recent # docs ...");
            ResultSequence rs = s.submitRequest(request);
            String[] results = rs.asStrings();
            LOG.info(String.format("PingInvoke - total documents: %s Execution time: %s", results[0], results[1]));
            s.close();
        } catch (RequestException e) {
            LOG.error(TestHelper.returnExceptionString(e));
        } */
    }
}
