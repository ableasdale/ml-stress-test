package com.marklogic.stresstest;

import com.marklogic.stresstest.jobs.PingGroupA;
import com.marklogic.stresstest.jobs.PingGroupB;
import com.marklogic.stresstest.providers.Configuration;
import com.marklogic.stresstest.providers.JerseyServer;
import com.marklogic.stresstest.util.Consts;
import com.marklogic.stresstest.util.TestHelper;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ableasdale on 8/21/2015.
 */
public class ABStressTest {
    private static Logger LOG = LoggerFactory.getLogger(StressTest.class);

    public static void main(String[] args) {

        // INIT
        LOG.info(String.format("Starting MarkLogic ** AB ** stress test: running for %d minute(s)", Configuration.getInstance().getDurationInMinutes()));
        TestHelper.getStressTestInstance().setTestDateTime(new Date());
        TestHelper.getStressTestInstance().setTestLabel(Configuration.getInstance().getTestLabel());
        TestHelper.getStressTestInstance().setTotalHosts(Configuration.getInstance().getUriList().size());
        TestHelper.getStressTestInstance().setHostTimingMaps(new ConcurrentHashMap<String, Map<String, List<String>>>());

        // JOBS
        JobDetail pingA = JobBuilder.newJob(PingGroupA.class).withIdentity("pingA").build();
        JobDetail pingB = JobBuilder.newJob(PingGroupB.class).withIdentity("pingB").build();

        /*
        JobDetail load = JobBuilder.newJob(Load.class).withIdentity("load").build();
        JobDetail load2 = JobBuilder.newJob(Load.class).withIdentity("load2").build();
        JobDetail merge = JobBuilder.newJob(ForceMerge.class).withIdentity("merge").build();
        */

        // TRIGGERS
        Trigger triggerPingA = TriggerBuilder.newTrigger().withIdentity("triggerPingA").withSchedule(CronScheduleBuilder.cronSchedule(Consts.EVERY_SECOND)).build();
        Trigger triggerPingB = TriggerBuilder.newTrigger().withIdentity("triggerPingB").withSchedule(CronScheduleBuilder.cronSchedule(Consts.EVERY_SECOND)).build();

        /*
        Trigger triggerPingInvoke = TriggerBuilder.newTrigger().withIdentity("triggerPingInvoke").withSchedule(CronScheduleBuilder.cronSchedule(Consts.EVERY_SECOND)).build();
        Trigger triggerVersionInvoke = TriggerBuilder.newTrigger().withIdentity("triggerVersionInvoke").withSchedule(CronScheduleBuilder.cronSchedule(Consts.EVERY_SECOND)).build();
        Trigger triggerLoad = TriggerBuilder.newTrigger().withIdentity("triggerLoad").withSchedule(CronScheduleBuilder.cronSchedule(Consts.EVERY_FIVE_SECONDS)).build();
        Trigger triggerLoad2 = TriggerBuilder.newTrigger().withIdentity("triggerLoad2").withSchedule(CronScheduleBuilder.cronSchedule(Consts.EVERY_TWO_SECONDS)).build();
        Trigger triggerMerge = TriggerBuilder.newTrigger().withIdentity("triggerMerge").withSchedule(CronScheduleBuilder.cronSchedule(Consts.EVERY_MINUTE)).build();
        */

        // SCHEDULER

        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();

            scheduler.scheduleJob(pingA, triggerPingA);
            scheduler.scheduleJob(pingB, triggerPingB);
            // scheduler.scheduleJob(pingInvoke, triggerPingInvoke);
            // scheduler.scheduleJob(versionInvoke, triggerVersionInvoke);
            // scheduler.scheduleJob(load, triggerLoad);
            // scheduler.scheduleJob(load2, triggerLoad2);
            // scheduler.scheduleJob(merge, triggerMerge);

            /* Another example of scheduler use from the docs....
              .startNow().withSchedule(simpleSchedule().withIntervalInSeconds(40).repeatForever()) */
            LOG.info("Stress test begins");
            scheduler.start();
            try {
                // Wait X seconds then kill the scheduler
                Thread.sleep(Consts.ONE_MINUTE * Configuration.getInstance().getDurationInMinutes());
            } catch (InterruptedException e) {
                TestHelper.returnExceptionString(e);
            }
            LOG.info("End of stress test");
            scheduler.shutdown(true);
        } catch (SchedulerException e) {
            LOG.error(TestHelper.returnExceptionString(e));
        }

        TestHelper.saveSessionData();

        // Set up jersey to run the report and generate a graph
        Thread t = new JerseyServer();
        t.start();

    }
}
