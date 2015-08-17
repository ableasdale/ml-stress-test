package com.marklogic.stresstest;

import com.marklogic.stresstest.consts.Consts;
import com.marklogic.stresstest.helpers.TestHelper;
import com.marklogic.stresstest.jobs.ForceMerge;
import com.marklogic.stresstest.jobs.Load;
import com.marklogic.stresstest.jobs.Ping;
import com.marklogic.stresstest.providers.Configuration;
import com.marklogic.stresstest.resources.BaseResource;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.freemarker.FreemarkerViewProcessor;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

        LOG.info("Starting MarkLogic stress test");

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

        // Crude report feature
        Iterator<String> iterator = TestHelper.timingsList.iterator();
        while (iterator.hasNext()) {
            LOG.info(iterator.next());
        }
        // Set up jersey?
        Thread t = new JerseyServer();
        t.start();

    }
}
