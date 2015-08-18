package com.marklogic.stresstest;

import com.marklogic.stresstest.consts.Consts;
import com.marklogic.stresstest.helpers.TestHelper;
import com.marklogic.stresstest.jobs.ForceMerge;
import com.marklogic.stresstest.jobs.Load;
import com.marklogic.stresstest.jobs.Ping;
import com.marklogic.stresstest.providers.Configuration;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: alexb
 * Date: 15/08/15
 * Time: 09:24
 * To change this template use File | Settings | File Templates.
 */
public class QuartzTest {

    private static Logger LOG = LoggerFactory.getLogger(QuartzTest.class);

    public static void main(String[] args) {

        LOG.info(String.format("Starting MarkLogic stress test: running for %d minute(s)", Configuration.getInstance().getDurationInMinutes()));

        // JOBS

        JobDetail ping = JobBuilder.newJob(Ping.class).withIdentity("ping").build();
        JobDetail load = JobBuilder.newJob(Load.class).withIdentity("load").build();
        JobDetail load2 = JobBuilder.newJob(Load.class).withIdentity("load2").build();
        JobDetail merge = JobBuilder.newJob(ForceMerge.class).withIdentity("merge").build();

        // TRIGGERS

        Trigger triggerPing = TriggerBuilder.newTrigger().withIdentity("triggerPing").withSchedule(CronScheduleBuilder.cronSchedule(Consts.EVERY_SECOND)).build();
        Trigger triggerLoad = TriggerBuilder.newTrigger().withIdentity("triggerLoad").withSchedule(CronScheduleBuilder.cronSchedule(Consts.EVERY_FIVE_SECONDS)).build();
        Trigger triggerLoad2 = TriggerBuilder.newTrigger().withIdentity("triggerLoad2").withSchedule(CronScheduleBuilder.cronSchedule(Consts.EVERY_TWO_SECONDS)).build();
        Trigger triggerMerge = TriggerBuilder.newTrigger().withIdentity("triggerMerge").withSchedule(CronScheduleBuilder.cronSchedule(Consts.EVERY_MINUTE)).build();

        // SCHEDULER

        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();

            scheduler.scheduleJob(ping, triggerPing);
            scheduler.scheduleJob(load, triggerLoad);
            scheduler.scheduleJob(load2, triggerLoad2);
            scheduler.scheduleJob(merge, triggerMerge);

            /* Another example of scheduler use from the docs....
              .startNow().withSchedule(simpleSchedule().withIntervalInSeconds(40).repeatForever()) */
            scheduler.start();
            try {
                // Wait X seconds then kill the scheduler
                Thread.sleep(Consts.ONE_MINUTE * Configuration.getInstance().getDurationInMinutes());
            } catch (InterruptedException e) {
                TestHelper.returnExceptionString(e);
            }

            scheduler.shutdown(true);
        } catch (SchedulerException e) {
            LOG.error(TestHelper.returnExceptionString(e));
        }

        // Set up jersey to run the report and generate a graph
        Thread t = new JerseyServer();
        t.start();

    }
}
