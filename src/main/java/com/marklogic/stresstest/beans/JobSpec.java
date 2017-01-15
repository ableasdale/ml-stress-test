package com.marklogic.stresstest.beans;

/**
 * Created by ableasdale on 15/01/2017.
 */
public class JobSpec implements java.io.Serializable {

    private String classname;
    private String username;
    private String password;
    private String accepts;
    private String groupname;
    private String endpoint;
    private String interval;

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccepts() {
        return accepts;
    }

    public void setAccepts(String accepts) {
        this.accepts = accepts;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
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
        if (username != null ? !username.equals(jobSpec.username) : jobSpec.username != null) return false;
        if (password != null ? !password.equals(jobSpec.password) : jobSpec.password != null) return false;
        if (accepts != null ? !accepts.equals(jobSpec.accepts) : jobSpec.accepts != null) return false;
        if (groupname != null ? !groupname.equals(jobSpec.groupname) : jobSpec.groupname != null) return false;
        if (endpoint != null ? !endpoint.equals(jobSpec.endpoint) : jobSpec.endpoint != null) return false;
        return interval != null ? interval.equals(jobSpec.interval) : jobSpec.interval == null;

    }

    @Override
    public int hashCode() {
        int result = classname != null ? classname.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (accepts != null ? accepts.hashCode() : 0);
        result = 31 * result + (groupname != null ? groupname.hashCode() : 0);
        result = 31 * result + (endpoint != null ? endpoint.hashCode() : 0);
        result = 31 * result + (interval != null ? interval.hashCode() : 0);
        return result;
    }
}
