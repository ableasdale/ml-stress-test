package com.marklogic.stresstest.helpers;

import com.marklogic.stresstest.beans.StressTest;
import com.marklogic.stresstest.consts.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.MessageFormat;
import java.util.UUID;

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

    public static void saveSessionData() {
        try {
            FileOutputStream fos =
                    new FileOutputStream(Consts.SAVE_DIRECTORY_ROOT + "\\" + UUID.randomUUID() + ".dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(TestHelper.getStressTestInstance());
            oos.close();
            fos.close();
            LOG.info("Session data saved");
        } catch (IOException e) {
            LOG.error(returnExceptionString(e));
        }
    }

    private static class StressTestDataProvider {
        private static final StressTest INSTANCE = new StressTest();
    }


}
