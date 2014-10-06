package net.nfiniteloop.loqale.backend;

import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.Key;
import com.googlecode.objectify.annotation.Entity;

/**
 * Created by vaek on 10/5/14.
 */
@Entity
public class User {
    private Key key;
    private String userId;
    private String displayName;
    private String hometown;
    private GeoPt location;

    public Key getKey() {
        return key;
    }

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
}
