package com.marklogic.stresstest.providers;

import com.marklogic.stresstest.util.TestManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: alexb
 * Date: 22/08/15
 * Time: 12:29
 * To change this template use File | Settings | File Templates.
 */
public class TestScheduler {

    private Scheduler scheduler;

    private TestScheduler() {
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
        } catch (SchedulerException e) {
            TestManager.returnExceptionString(e);
        }
    }

    ;


    public static Scheduler getScheduler() {
        return SchedulerProvider.INSTANCE.scheduler;
    }

    private static class SchedulerProvider {
        private static final TestScheduler INSTANCE = new TestScheduler();
    }
}
