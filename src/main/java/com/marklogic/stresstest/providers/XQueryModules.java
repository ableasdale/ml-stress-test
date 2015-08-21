package com.marklogic.stresstest.providers;

import com.marklogic.stresstest.util.TestHelper;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: alexb
 * Date: 16/08/15
 * Time: 22:27
 * To change this template use File | Settings | File Templates.
 */
public class XQueryModules {

    private static final Logger LOG = LoggerFactory.getLogger(XQueryModules.class);

    private String LOAD_XML_DOC;
    private String PING_MARKLOGIC;

    private XQueryModules() {

        LOG.debug("Preparing XQuery Modules");
        try {
            LOAD_XML_DOC = FileUtils.readFileToString(new File("src/main/resources/queries/load.xqy"));
            PING_MARKLOGIC = FileUtils.readFileToString(new File("src/main/resources/queries/ping.xqy"));
        } catch (IOException e) {
            LOG.error(TestHelper.returnExceptionString(e));
        }
    }

    private static class XQueryModulesProvider {
        private static final XQueryModules INSTANCE = new XQueryModules();
    }


    public static XQueryModules getInstance() {
        return XQueryModulesProvider.INSTANCE;
    }

    public String loadXML() {
        return LOAD_XML_DOC;
    }

    public String pingMarkLogic() {
        return PING_MARKLOGIC;
    }

}
