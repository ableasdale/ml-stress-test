package com.marklogic.stresstest;

import com.marklogic.stresstest.beans.JobSpec;
import com.marklogic.stresstest.util.TestManager;

/**
 * Created by ableasdale on 8/21/2015.
 */
public class ABStressTest {

    public static void main(String[] args) {

        // Set up
        TestManager.initialize();

        TestManager.configureJobs();
        /* Add Jobs
        TestManager.addJob(PingGroupA.class, Consts.EVERY_SECOND);
        TestManager.addJob(HTTPEndpointGetRequest.class, Consts.EVERY_FIVE_SECONDS);
        TestManager.addJob(RestEndpointRequest.class, Consts.EVERY_FIVE_SECONDS);
        TestManager.addJob(PingGroupB.class, Consts.EVERY_TWO_SECONDS);
        TestManager.addJob(ForceMerge.class, Consts.EVERY_TEN_SECONDS); */

        // TESTING
        TestManager.runHttpReport();

        // Run
        TestManager.runTest();

        // Generate HTML report
        TestManager.saveSessionData();
    }
}
