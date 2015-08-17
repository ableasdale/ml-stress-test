package com.marklogic.stresstest.helpers;

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

    public static List<String> timingsList = Collections.synchronizedList(new ArrayList<String>());

    public static String returnExceptionString(Exception e) {
        return MessageFormat.format("{0} caught: {1}", e.getClass().getName(),
                e);
    }
}
