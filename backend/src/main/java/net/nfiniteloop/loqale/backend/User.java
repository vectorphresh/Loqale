package net.nfiniteloop.loqale.backend;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaek on 10/5/14.
 * TODO: Keys are not supported in the endpoint classes. Will resolve following the semester
 */
@Entity
public class User {
    @Id
    private Long id;
    @Index
    private String userId;
    private String displayName;
    private String hometown;
    private GeoPt location;
    private Double proximity;

    private List<String> categories = new ArrayList<String>();

    private String deviceId;

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getHometown() {
        return hometown;
    }

    public GeoPt getLocation() {
        return location;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public void setLocation(GeoPt location) {
        this.location = location;
    }

    public void setCategories(List<String> userPreferredCategories){
        categories = userPreferredCategories;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setProximity(Double proximity) {
        this.proximity = proximity;
    }

    public Double getProximity() {
        return proximity;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }
}
