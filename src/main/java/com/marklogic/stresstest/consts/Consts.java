package com.marklogic.stresstest.consts;

import java.text.MessageFormat;

/**
 * Created with IntelliJ IDEA.
 * User: alexb
 * Date: 15/08/15
 * Time: 09:52
 * To change this template use File | Settings | File Templates.
 */
public class Consts {

    public static String EVERY_SECOND = "0/1 * * * * ?";
    public static String EVERY_FIVE_SECONDS = "0/5 * * * * ?";


    /**
     * The Constant CONFIG_FILE_PATH.
     */
    public static final String CONFIG_FILE_PATH = "config.xml";


    // TODO - move to helper?
    public static String returnExceptionString(Exception e) {
        return MessageFormat.format("{0} caught: {1}", e.getClass().getName(),
                e);
    }
}
