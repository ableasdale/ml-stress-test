package com.marklogic.stresstest.beans;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Data about each test is stored in this Object - which will then be saved to disk for retrieval later.
 *
 * User: Alex
 * Date: 19/08/15
 * Time: 10:17
 */
public class StressTest implements java.io.Serializable {

    private String testLabel;
    private Date testDateTime;
    private int totalHosts;
    private Map<String, Map<String, List<String>>> hostTimingMaps;

    public String getTestLabel() {
        return testLabel;
    }

    public void setTestLabel(String testLabel) {
        this.testLabel = testLabel;
    }

    public Date getTestDateTime() {
        return testDateTime;
    }

    public void setTestDateTime(Date testDateTime) {
        this.testDateTime = testDateTime;
    }

    public int getTotalHosts() {
        return totalHosts;
    }

    public void setTotalHosts(int totalHosts) {
        this.totalHosts = totalHosts;
    }

    public Map<String, Map<String, List<String>>> getHostTimingMaps() {
        return hostTimingMaps;
    }

    public void setHostTimingMaps(Map<String, Map<String, List<String>>> hostTimingMaps) {
        this.hostTimingMaps = hostTimingMaps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StressTest that = (StressTest) o;

        if (totalHosts != that.totalHosts) return false;
        if (testLabel != null ? !testLabel.equals(that.testLabel) : that.testLabel != null) return false;
        if (testDateTime != null ? !testDateTime.equals(that.testDateTime) : that.testDateTime != null) return false;
        return !(hostTimingMaps != null ? !hostTimingMaps.equals(that.hostTimingMaps) : that.hostTimingMaps != null);

    }

    @Override
    public int hashCode() {
        int result = testLabel != null ? testLabel.hashCode() : 0;
        result = 31 * result + (testDateTime != null ? testDateTime.hashCode() : 0);
        result = 31 * result + totalHosts;
        result = 31 * result + (hostTimingMaps != null ? hostTimingMaps.hashCode() : 0);
        return result;
    }
}
