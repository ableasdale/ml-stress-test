package com.marklogic.stresstest.providers;

import com.marklogic.stresstest.beans.JobSpec;
import com.marklogic.stresstest.util.Consts;
import com.marklogic.stresstest.util.TestManager;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.*;

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
    private String testLabel;
    private Long durationInMinutes;
    private List<JobSpec> jobSpecList;

    private Configuration() {
        try {
            config = new XMLConfiguration(Consts.CONFIG_FILE_PATH);
        } catch (ConfigurationException e) {
            LOG.error(TestManager.returnExceptionString(e));
        }
        testLabel = config.getString("testLabel");
        durationInMinutes = config.getLong("durationInMinutes");
        uriList = Arrays.asList(config.getStringArray("uris.uri"));

        // Get the list of configured jobs from the config file
        List<HierarchicalConfiguration> jobs = config.configurationsAt("jobs.job");
        LOG.info(String.format("Total number of Jobs found: %d", jobs.size()));
        jobSpecList = new ArrayList<JobSpec>();

        for(HierarchicalConfiguration job : jobs) {
            JobSpec js = new JobSpec();
            js.setClassname(job.getString("classname"));
            js.setEndpoint(job.getString("endpoint"));
            js.setInterval(job.getString("interval"));

            LOG.debug(String.format("Adding Job: %s", job.getString("classname")));
            LOG.debug(String.format(" - %s", job.getString("endpoint")));
            LOG.debug(String.format(" - %s", job.getString("interval")));
            jobSpecList.add(js);
        }

        LOG.debug(MessageFormat.format("Number of xcc uris: {0}", uriList.size()));

    }

    public static Configuration getInstance() {
        return ConfigurationProvider.INSTANCE;
    }

    public XMLConfiguration getConfig() {
        return config;
    }

    public String getTestLabel() {
        return testLabel;
    }

    public List<String> getUriList() {
        return uriList;
    }

    public Long getDurationInMinutes() {
        return durationInMinutes;
    }

    public List<JobSpec> getJobSpecList() {return jobSpecList;}

    private static class ConfigurationProvider {
        private static final Configuration INSTANCE = new Configuration();
    }

}