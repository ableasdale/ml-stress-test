package com.marklogic.stresstest.jobs;

/**
 * Created with IntelliJ IDEA.
 * User: alexb
 * Date: 15/08/15
 * Time: 10:02
 * To change this template use File | Settings | File Templates.
 */

import com.marklogic.stresstest.consts.Consts;
import com.marklogic.stresstest.providers.SingleNodeMarkLogicContentSource;
import com.marklogic.xcc.exceptions.RequestException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Ping implements Job {
    private Logger LOG = LoggerFactory.getLogger(Ping.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {


        try {
            LOG.info(String.format("Ping: MarkLogic Current Query Timestamp: %s", SingleNodeMarkLogicContentSource.getInstance().getSession().getCurrentServerPointInTime()));
        } catch (RequestException e) {
            LOG.error(Consts.returnExceptionString(e));
        }


    }

}