package com.marklogic.stresstest.providers;

import com.marklogic.stresstest.util.TestManager;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

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
            LOG.error(TestManager.returnExceptionString(e));
        }
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

    public String adhocQuery(String endpoint) {
        try {
            //LOG.info("src/main/resources/queries/"+endpoint);
            return FileUtils.readFileToString(new File("src/main/resources/queries/"+endpoint), Charset.forName("UTF-8"));
        } catch (IOException e) {
            LOG.error(TestManager.returnExceptionString(e));
        }
        return "Error: Unable to process Module "+endpoint;
    }

    private static class XQueryModulesProvider {
        private static final XQueryModules INSTANCE = new XQueryModules();
    }

}
