package com.marklogic.stresstest.helpers;

import com.marklogic.stresstest.beans.StressTest;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alexb
 * Date: 16/08/15
 * Time: 21:49
 * To change this template use File | Settings | File Templates.
 */
public class TestHelper {

    private static class StressTestDataProvider {
        private static final StressTest INSTANCE = new StressTest();
    }

    public static StressTest getStressTestInstance() {
        return StressTestDataProvider.INSTANCE;
    }

    public static String returnExceptionString(Exception e) {
        return MessageFormat.format("{0} caught: {1}", e.getClass().getName(),
                e);
    }
}
