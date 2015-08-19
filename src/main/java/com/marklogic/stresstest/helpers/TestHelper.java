package com.marklogic.stresstest.helpers;

import com.marklogic.stresstest.beans.StressTest;
import com.marklogic.stresstest.consts.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
            FileOutputStream fos = new FileOutputStream(Consts.SAVE_DIRECTORY_ROOT + "\\" + UUID.randomUUID() + ".ml");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(TestHelper.getStressTestInstance());
            oos.close();
            fos.close();
            LOG.info("Session data saved");
        } catch (IOException e) {
            LOG.error(returnExceptionString(e));
        }
    }

    public static void loadSessionData(String fname) {
        StressTest st = null;
        try {
            FileInputStream fin = new FileInputStream(Consts.SAVE_DIRECTORY_ROOT + "\\" + fname);
            ObjectInputStream in = new ObjectInputStream(fin);
            st = (StressTest) in.readObject();
            in.close();
            fin.close();
        } catch (IOException e) {
            LOG.error(returnExceptionString(e));
        } catch (ClassNotFoundException c) {
            LOG.error(returnExceptionString(c));
        }
        if (st != null){
            TestHelper.getStressTestInstance().setTestLabel(st.getTestLabel());
            TestHelper.getStressTestInstance().setTestDateTime(st.getTestDateTime());
            TestHelper.getStressTestInstance().setHostTimings(st.getHostTimings());
            TestHelper.getStressTestInstance().setTotalHosts(st.getTotalHosts());
        }
    }

    public static List<String> getSaveDirectoryListing() {
        File f = new File(Consts.SAVE_DIRECTORY_ROOT);
        //List<String> filenames = new
        //ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
        //File[] list = f.listFiles();
        //for (File fi : f.listFiles()){

        //}
        return new ArrayList<String>(Arrays.asList(f.list()));
    }

    private static class StressTestDataProvider {
        private static final StressTest INSTANCE = new StressTest();
    }

}
