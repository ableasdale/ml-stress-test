package com.marklogic.stresstest.beans;

/**
 * Created by ableasdale on 15/01/2017.
 */
public class JobSpec implements java.io.Serializable {

    private String classname;
    private String endpoint;
    private String interval;

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobSpec jobSpec = (JobSpec) o;

        if (classname != null ? !classname.equals(jobSpec.classname) : jobSpec.classname != null) return false;
        if (endpoint != null ? !endpoint.equals(jobSpec.endpoint) : jobSpec.endpoint != null) return false;
        return interval != null ? interval.equals(jobSpec.interval) : jobSpec.interval == null;

    }

    @Override
    public int hashCode() {
        int result = classname != null ? classname.hashCode() : 0;
        result = 31 * result + (endpoint != null ? endpoint.hashCode() : 0);
        result = 31 * result + (interval != null ? interval.hashCode() : 0);
        return result;
    }
}
