package com.marklogic.stresstest.providers;

import com.marklogic.stresstest.helpers.TestHelper;
import com.marklogic.xcc.ContentSource;
import com.marklogic.xcc.ContentSourceFactory;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.XccConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * This class can likely be deprecated in later releases...
 */
public class SingleNodeMarkLogicContentSource {

    private static final Logger LOG = LoggerFactory.getLogger(SingleNodeMarkLogicContentSource.class);
    private ContentSource cs;

    private SingleNodeMarkLogicContentSource() {
        try {
            cs = ContentSourceFactory.newContentSource(new URI(Configuration.getInstance().getUriList().get(0)));
        } catch (XccConfigException e) {
            LOG.error(TestHelper.returnExceptionString(e));
        } catch (URISyntaxException e) {
            LOG.error(TestHelper.returnExceptionString(e));
        }
    }

    public static SingleNodeMarkLogicContentSource getInstance() {
        return ContentSourceProviderHolder.INSTANCE;
    }

    public Session getSession() {
        return cs.newSession();
    }

    private static class ContentSourceProviderHolder {
        private static final SingleNodeMarkLogicContentSource INSTANCE = new SingleNodeMarkLogicContentSource();
    }

}