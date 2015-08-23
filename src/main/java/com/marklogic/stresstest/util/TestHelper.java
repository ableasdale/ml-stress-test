package com.marklogic.stresstest.util;

import com.marklogic.stresstest.beans.StressTest;
import com.marklogic.stresstest.providers.Configuration;
import com.marklogic.stresstest.providers.JerseyServer;
import com.marklogic.stresstest.providers.LoadBalancedMarkLogicContentSource;
import com.marklogic.stresstest.providers.TestScheduler;
import com.marklogic.xcc.ContentSource;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * Created with IntelliJ IDEA.
 * User: alexb
 * Date: 16/08/15
 * Time: 21:49
 * To change this template use File | Settings | File Templates.
 */
public class TestHelper {

    private static Logger LOG = LoggerFactory.getLogger(TestHelper.class);

    public static StressTest getStressTestInstance() {
        return StressTestDataProvider.INSTANCE;
    }

    public static String returnExceptionString(Exception e) {
        return MessageFormat.format("{0} caught: {1}", e.getClass().getName(),
                e);
    }

    public static void addResultToTimingMap(String timingGroup, String uri, String result) {
        if (TestHelper.getStressTestInstance().getHostTimingMaps().containsKey(timingGroup)) {
            List<String> timingsList = TestHelper.getStressTestInstance().getHostTimingMaps().get(timingGroup).get(uri);
            timingsList.add(result);
            TestHelper.getStressTestInstance().getHostTimingMaps().get(timingGroup).put(uri, timingsList);
        } else {
            Map<String, List<String>> tm = new ConcurrentHashMap<String, List<String>>();
            List<ContentSource> csl = LoadBalancedMarkLogicContentSource.getInstance().getCopyOfActiveContentSourceList();
            for (ContentSource cs : csl) {
                tm.put(cs.getConnectionProvider().getHostName(), new CopyOnWriteArrayList<String>());
            }
            TestHelper.getStressTestInstance().getHostTimingMaps().put(timingGroup, tm);
            TestHelper.addResultToTimingMap(timingGroup, uri, result);
        }
    }

    public static void saveSessionData() {
        try {
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hhmmss");
            String filenameDate = sdf.format(d);
            FileOutputStream fos = new FileOutputStream(Consts.SAVE_DIRECTORY_ROOT + File.separator + Configuration.getInstance().getTestLabel() + "-" + filenameDate + ".ml");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(TestHelper.getStressTestInstance());
            oos.close();
            fos.close();
            LOG.debug("Session data saved");
        } catch (IOException e) {
            LOG.error(returnExceptionString(e));
        }
    }

    public static void loadSessionData(String fname) {
        StressTest st = null;
        try {
            FileInputStream fin = new FileInputStream(Consts.SAVE_DIRECTORY_ROOT + File.separator + fname);
            ObjectInputStream in = new ObjectInputStream(fin);
            st = (StressTest) in.readObject();
            in.close();
            fin.close();
        } catch (IOException e) {
            LOG.error(returnExceptionString(e));
        } catch (ClassNotFoundException c) {
            LOG.error(returnExceptionString(c));
        }
        if (st != null) {
            TestHelper.getStressTestInstance().setTestLabel(st.getTestLabel());
            TestHelper.getStressTestInstance().setTestDateTime(st.getTestDateTime());
            TestHelper.getStressTestInstance().setHostTimingMaps(st.getHostTimingMaps());
            TestHelper.getStressTestInstance().setTotalHosts(st.getTotalHosts());
        }
    }

    public static void deleteSavedSessionData(String name) {
        new File(Consts.SAVE_DIRECTORY_ROOT + File.separator + name).delete();
    }

    public static File[] getSaveDirectoryFiles() {
        return new File(Consts.SAVE_DIRECTORY_ROOT).listFiles();
    }

    public static List<String> getSaveDirectoryListing() {
        return new ArrayList<String>(Arrays.asList(new File(Consts.SAVE_DIRECTORY_ROOT).list()));
    }

    public static void initialize() {
        LOG.info(String.format("Starting MarkLogic ** AB ** stress test: running for %d minute(s)", Configuration.getInstance().getDurationInMinutes()));
        getStressTestInstance().setTestDateTime(new Date());
        getStressTestInstance().setTestLabel(Configuration.getInstance().getTestLabel());
        getStressTestInstance().setTotalHosts(Configuration.getInstance().getUriList().size());
        getStressTestInstance().setHostTimingMaps(new ConcurrentHashMap<String, Map<String, List<String>>>());
    }

    public static void saveSessionDataAndReport() {
        TestHelper.saveSessionData();
        // Set up jersey to run the report and generate a graph
        Thread t = new JerseyServer();
        t.start();
    }

    public static void runTest() {
        try {
            LOG.info("Test starting");
            TestScheduler.getScheduler().start();
            // Wait X seconds then kill the scheduler
            Thread.sleep(Consts.ONE_MINUTE * Configuration.getInstance().getDurationInMinutes());
            TestScheduler.getScheduler().shutdown(true);
            //getStressTestInstance().setScheduler(scheduler);

            //for(String group: scheduler.getJobGroupNames()) {
            // enumerate each job in group

                /*
                for(JobKey jobKey : scheduler.getJobKeys((Key) groupEquals(group))) {
                    System.out.println("Found job identified by: " + jobKey);
                    LOG.info(scheduler.getJobDetail(jobKey).getDescription();
                } */
            //}

            LOG.info("Test complete");
        } catch (SchedulerException e) {
            returnExceptionString(e);
        } catch (InterruptedException e) {
            returnExceptionString(e);
        }

    }

    // TODO public static void addJob(Class<? extends Job> job, int interval) {

    public static void addJob(Class<? extends Job> job, String interval) {
        LOG.info("add job"+job.getCanonicalName());
        JobDetail jd = JobBuilder.newJob(job).withIdentity(job.getSimpleName()).build();
        LOG.info(job.getSimpleName());

        // TODO - this currently only works with CRON - do we pass the cron in for the interval? Perhaps?

        // Trigger t = TriggerBuilder.newTrigger().withIdentity("trigger" + job.getSimpleName()).withSchedule(simpleSchedule().withIntervalInSeconds(interval).repeatForever()).build();
        Trigger t = TriggerBuilder.newTrigger().withIdentity("trigger" + job.getSimpleName()).withSchedule(CronScheduleBuilder.cronSchedule(interval)).build();
        //Trigger triggerPingA = TriggerBuilder.newTrigger().withIdentity("triggerPingA").withSchedule(CronScheduleBuilder.cronSchedule(Consts.EVERY_SECOND)).build();

        try {
            TestScheduler.getScheduler().scheduleJob(jd, t);
        } catch (SchedulerException e) {
            returnExceptionString(e);
        }

    }

    private static class StressTestDataProvider {
        private static final StressTest INSTANCE = new StressTest();
    }

}
