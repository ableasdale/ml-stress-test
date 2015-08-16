package com.marklogic.stresstest;

import com.marklogic.stresstest.consts.Consts;
import com.marklogic.stresstest.helpers.TestHelper;
import com.marklogic.stresstest.jobs.ForceMerge;
import com.marklogic.stresstest.jobs.Load;
import com.marklogic.stresstest.jobs.Ping;
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

        LOG.info("Starting stress test");

        // JOBS

        JobDetail ping = JobBuilder.newJob(Ping.class).withIdentity("ping").build();
        JobDetail load = JobBuilder.newJob(Load.class).withIdentity("load").build();
        JobDetail merge = JobBuilder.newJob(ForceMerge.class).withIdentity("merge").build();

        // TRIGGERS

        Trigger triggerPing = TriggerBuilder.newTrigger().withIdentity("triggerPing").withSchedule(CronScheduleBuilder.cronSchedule(Consts.EVERY_SECOND)).build();
        Trigger triggerLoad = TriggerBuilder.newTrigger().withIdentity("triggerLoad").withSchedule(CronScheduleBuilder.cronSchedule(Consts.EVERY_FIVE_SECONDS)).build();
        Trigger triggerMerge = TriggerBuilder.newTrigger().withIdentity("triggerMerge").withSchedule(CronScheduleBuilder.cronSchedule(Consts.EVERY_MINUTE)).build();

        // SCHEDULER

        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();

            scheduler.scheduleJob(ping, triggerPing);
            scheduler.scheduleJob(load, triggerLoad);
            scheduler.scheduleJob(merge, triggerMerge);

            scheduler.start();
        } catch (SchedulerException e) {
            LOG.error(TestHelper.returnExceptionString(e));
        }


    }
}
