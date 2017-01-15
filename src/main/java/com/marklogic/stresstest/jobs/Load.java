package com.marklogic.stresstest.jobs; /**
 * Created with IntelliJ IDEA.
 * User: alexb
 * Date: 15/08/15
 * Time: 09:25
 * To change this template use File | Settings | File Templates.
 */

import com.marklogic.stresstest.providers.LoadBalancedMarkLogicContentSource;
import com.marklogic.stresstest.providers.XQueryModules;
import com.marklogic.stresstest.util.TestManager;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Load implements Job {
    private Logger LOG = LoggerFactory.getLogger(Load.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {

        Session s = LoadBalancedMarkLogicContentSource.getInstance().openSession();
        try {
            LOG.debug("Generating docs.");
            s.submitRequest(s.newAdhocQuery(XQueryModules.getInstance().loadXML()));
            s.close();
        } catch (RequestException e) {
            LOG.error(TestManager.returnExceptionString(e));
        }

    }

}
