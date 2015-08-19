package com.marklogic.stresstest.consts;

/**
 * Created with IntelliJ IDEA.
 * User: alexb
 * Date: 15/08/15
 * Time: 09:52
 * To change this template use File | Settings | File Templates.
 */
public class Consts {

    public static String EVERY_SECOND = "0/1 * * * * ?";
    public static String EVERY_TWO_SECONDS = "0/2 * * * * ?";
    public static String EVERY_FIVE_SECONDS = "0/5 * * * * ?";
    public static String EVERY_MINUTE = "0/60 * * * * ?";

    public static final String CONFIG_FILE_PATH = "config.xml";

    public static Long ONE_SECOND = 1L * 1000L;
    public static Long ONE_MINUTE = 60L * 1000L;
    public static Long ONE_HOUR = 3600L * 1000L;

    public static int GRIZZLY_HTTP_PORT = 9995;

    public static String STATIC_RESOURCE_DIRECTORY_ROOT = System.getProperty("user.dir") + "\\src\\main\\resources\\vendor";
    public static String SAVE_DIRECTORY_ROOT = System.getProperty("user.dir") + "\\src\\main\\resources\\saved";
    //public static String FREEMARKER_TEMPLATE_PATH = "src/main/resources/freemarker";
}
