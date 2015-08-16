package com.marklogic.stresstest.jobs; /**
 * Created with IntelliJ IDEA.
 * User: alexb
 * Date: 15/08/15
 * Time: 09:25
 * To change this template use File | Settings | File Templates.
 */

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Load implements Job {
    private Logger LOG = LoggerFactory.getLogger(Load.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {

        LOG.info("Load some data");

    }

}