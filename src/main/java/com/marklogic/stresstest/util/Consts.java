package com.marklogic.stresstest.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: alexb
 * Date: 15/08/15
 * Time: 09:52
 * To change this template use File | Settings | File Templates.
 */
public class Consts {

    public static final String CONFIG_FILE_PATH = "config.xml";
    public static String EVERY_SECOND = "0/1 * * * * ?";
    public static String EVERY_TWO_SECONDS = "0/2 * * * * ?";
    public static String EVERY_FIVE_SECONDS = "0/5 * * * * ?";
    public static String EVERY_TEN_SECONDS = "0/10 * * * * ?";
    public static String EVERY_TWENTY_SECONDS = "0/20 * * * * ?";
    public static String EVERY_MINUTE = "0/60 * * * * ?";
    public static Long ONE_SECOND = 1L * 1000L;
    public static Long TEN_SECONDS = 10L * 1000L;
    public static Long ONE_MINUTE = 60L * 1000L;
    public static Long ONE_HOUR = 3600L * 1000L;

    public static int GRIZZLY_HTTP_PORT = 9995;

    public static String BASE_DIRECTORY_ROOT = new StringBuilder().append(System.getProperty("user.dir")).append(File.separator).append(StringUtils.join(new String[]{"src", "main", "resources"}, File.separator)).toString();
    public static String STATIC_RESOURCE_DIRECTORY_ROOT = new StringBuilder().append(BASE_DIRECTORY_ROOT).append(File.separator).append("vendor").toString();
    public static String SAVE_DIRECTORY_ROOT = new StringBuilder().append(BASE_DIRECTORY_ROOT).append(File.separator).append("saved").toString();
}
