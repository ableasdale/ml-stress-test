package com.marklogic.stresstest.providers;

import com.marklogic.stresstest.consts.Consts;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alexb
 * Date: 16/08/15
 * Time: 20:42
 * To change this template use File | Settings | File Templates.
 */
public class Configuration {

    private static final Logger LOG = LoggerFactory.getLogger(Configuration.class);
    private XMLConfiguration config;
    private List<String> uriList;

    private Configuration() {
        try {
            config = new XMLConfiguration(Consts.CONFIG_FILE_PATH);
        } catch (ConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        uriList = Arrays.asList(config.getStringArray("uris.uri"));
        LOG.info(MessageFormat.format("Number of xcc uris: {0}", uriList.size()));

    }

    private static class ConfigurationProvider {
        private static final Configuration INSTANCE = new Configuration();
    }


    public static Configuration getInstance() {
        return ConfigurationProvider.INSTANCE;
    }

    public XMLConfiguration getConfig() {
        return config;
    }

    public List<String> getUriList() {
        return uriList;
    }

}