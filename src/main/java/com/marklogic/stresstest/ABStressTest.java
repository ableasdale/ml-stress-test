package com.marklogic.stresstest;

import com.marklogic.stresstest.jobs.PingGroupA;
import com.marklogic.stresstest.jobs.PingGroupB;
import com.marklogic.stresstest.util.TestHelper;

/**
 * Created by ableasdale on 8/21/2015.
 */
public class ABStressTest {

    public static void main(String[] args) {

        // Set up
        TestHelper.initialize();

        // Add Jobs
        TestHelper.addJob(PingGroupA.class, 1);
        TestHelper.addJob(PingGroupB.class, 1);

        // Run
        TestHelper.runTest();

        // Generate HTML report
        TestHelper.saveSessionDataAndReport();

    }
}
