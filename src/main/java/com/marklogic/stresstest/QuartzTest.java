package com.marklogic.stresstest;

import com.marklogic.stresstest.consts.Consts;
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

        JobDetail job = JobBuilder.newJob(Load.class)
                .withIdentity("dummyJobName", "group1").build();

        JobDetail pingJob = JobBuilder.newJob(Ping.class)
                .withIdentity("pingJob", "group1").build();


        Trigger triggerA = TriggerBuilder
                .newTrigger()
                .withIdentity("pingJob", "group1")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule(Consts.EVERY_SECOND))
                .build();


        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("dummyTriggerName", "group1")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule(Consts.EVERY_FIVE_SECONDS))
                .build();


        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(pingJob, triggerA);
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
