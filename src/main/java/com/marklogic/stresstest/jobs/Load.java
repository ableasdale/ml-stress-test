package com.marklogic.stresstest.jobs; /**
 * Created with IntelliJ IDEA.
 * User: alexb
 * Date: 15/08/15
 * Time: 09:25
 * To change this template use File | Settings | File Templates.
 */

import com.marklogic.stresstest.helpers.TestHelper;
import com.marklogic.stresstest.providers.SingleNodeMarkLogicContentSource;
import com.marklogic.stresstest.providers.XQueryModules;
import com.marklogic.xcc.Request;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;
import org.apache.commons.io.FileUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;


public class Load implements Job {
    private Logger LOG = LoggerFactory.getLogger(Load.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {

        Session s = SingleNodeMarkLogicContentSource.getInstance().getSession();
        try {
            LOG.info("Loading 1500 docs.");
            s.submitRequest(s.newAdhocQuery(XQueryModules.getInstance().loadXML()));
            s.close();
        } catch (RequestException e) {
            LOG.error(TestHelper.returnExceptionString(e));
        }

    }

}
