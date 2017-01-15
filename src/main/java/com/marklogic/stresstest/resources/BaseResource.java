package com.marklogic.stresstest.resources;

import com.sun.jersey.api.view.Viewable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.net.URI;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.text.MessageFormat.format;

/**
 * Created with IntelliJ IDEA. User: ableasdale Date: 2/1/14 Time: 6:10 PM
 */

public class BaseResource {

    private static final Logger LOG = LoggerFactory.getLogger(BaseResource.class);

    public List<File> files = new ArrayList<File>();
    @Context
    protected UriInfo uriInfo;

    public BaseResource() {
        LOG.debug("Base Constructor :: Init");
    }


    protected Map<String, Object> createModel() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("applicationTitle", "MarkLogic Stress Testing Tool");
        return map;
    }

    /**
     * Attempt to make sure input is an integer.
     *
     * @param text the value passed to the method from the resource (a URI
     *             segment)
     * @return true or false
     */
    protected boolean canBeParsedAsInteger(String text) {
        try {
            new Integer(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Freemarker template parameter map for the HTTP Exception Page template
     * (exception.ftl)
     *
     * @param statusCode
     * @param message
     * @return
     */
    protected Map<String, Object> createExceptionModel(int statusCode,
                                                       String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", String.valueOf(statusCode));
        map.put("message", message);
        return map;
    }

    protected Response generalExceptionCheck(String id, int arrayBoundary) {
        Response r = null;
        // non-string check
        if (!canBeParsedAsInteger(id)) {
            r = wrapViewableExceptionResponse(format(
                    "<em>{0}</em> does not appear to be a valid integer.", id));
        } else {
            // bounds check
            if (Integer.parseInt(id) < 0) {
                r = wrapViewableExceptionResponse(format(
                        "Bounds check failed; It looks like you have requested a value ({0}) smaller than the number of stacks available ({1})",
                        id, arrayBoundary));
            }
            if (Integer.parseInt(id) > arrayBoundary) {
                r = wrapViewableExceptionResponse(format(
                        "Bounds check failed; It looks like you have requested a value ({0}) larger than the number of stacks available ({1})",
                        id, arrayBoundary));
            }
        }
        return r;
    }

    /**
     * Trims the String output by pstack to include only the important
     * information
     *
     * @param str
     * @return
     */
    private String getThreadInfo(String str) {
        return str.substring((str.indexOf("(") + 1), str.lastIndexOf(")"));
    }

    public URI getUri(Class c) {
        return uriInfo.getBaseUriBuilder().path(c).build();
    }

    /**
     * General handler for exceptions in the request made to the resource This
     * is how you do a custom com.marklogic.analyser.Server Exception with the Freemarker template
     *
     * @param message
     * @return
     */
    protected Response wrapViewableExceptionResponse(String message) {
        LOG.error(MessageFormat.format("Exception encountered: {0}", message));
        return Response
                .status(500)
                .entity(new Viewable("/exception", createExceptionModel(500,
                        message))).build();
    }

    /**
     * Wraps an 'OK' response with the Viewable (freemarker) template
     *
     * @param templateName
     * @param model
     * @return
     */
    protected Response wrapViewableResponse(String templateName,
                                            Map<String, Object> model) {
        return Response.status(Response.Status.OK)
                .entity(new Viewable(templateName, model)).build();
    }

    /**
     * Creates a string that looks like this:
     * <p/>
     * ['PingGroupA response time (seconds)', '0.1', '0.2', '0.3']
     *
     * @param m
     * @return
     */
    protected String formatForChart(Map<String, List<String>> m) {
        List<String> chartData = new ArrayList<String>();
        for (String s : m.keySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append("['").append(s).append(" ping response time (s)',");
            sb.append(StringUtils.join(m.get(s), ","));
            sb.append("]");
            chartData.add(sb.toString());
        }
        return StringUtils.join(chartData, ",\n");
    }
}