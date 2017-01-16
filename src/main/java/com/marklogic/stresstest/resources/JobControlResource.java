package com.marklogic.stresstest.resources;

import com.marklogic.stresstest.providers.TestScheduler;
import com.marklogic.stresstest.util.TestManager;
import com.sun.jersey.api.view.Viewable;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 24/08/15
 * Time: 21:18
 * To change this template use File | Settings | File Templates.
 */
@Path("/jobs")
public class JobControlResource extends BaseResource  {

    private static final Logger LOG = LoggerFactory.getLogger(JobControlResource.class);

    // data model for freemarker .ftl template
    private Map<String, Object> createModel(String id) {
        Map map = createModel();
        map.put("title", "Active Jobs");
        map.put("metrics", TestManager.getStressTestInstance());

        //map.put("chartMap", lhm);
        return map;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable getDashboard() {
        LOG.info("Getting Dashboard ...");
        /*
        for(JobKey jk : TestScheduler.getScheduler().getJobKeys()){
            LOG.info("Key:"+jk.getName());
        }*/

        Scheduler scheduler = TestScheduler.getScheduler();

        try {
            for (String groupName : scheduler.getJobGroupNames()) {

                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

                    //jobKey.
                    String jobName = jobKey.getName();
                    String jobGroup = jobKey.getGroup();

                    //get job's trigger
                    List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                    Date nextFireTime = triggers.get(0).getNextFireTime();

                    LOG.info("[jobName] : " + jobName + " [groupName] : "
                            + jobGroup + " - " + nextFireTime);

                }

            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }


        return new Viewable("/jobs", createModel("Running Jobs"));
    }

    @GET
    @Path("stop/{key}")
    @Produces(MediaType.TEXT_HTML)
    public Response stopJob(@PathParam("key") String key) {

        try {
            TestScheduler.getScheduler().deleteJob(JobKey.jobKey(key));

            Iterator<String> iter = TestManager.getStressTestInstance().getTestOverview().iterator();
            while(iter.hasNext()){
                String t = iter.next();
                if(t.substring(0, t.indexOf(" ")).equals(key))
                    iter.remove();
            }

        } catch (SchedulerException e) {
            TestManager.returnExceptionString(e);
        }

        URI uri = UriBuilder.fromPath("/jobs").build();
        return Response.seeOther(uri).build();
    }
    //
}
