package net.nfiniteloop.loqale.backend;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Serialize;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaek on 10/5/14.
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
    List<Key<User>> friends = new ArrayList<Key<User>>();

    @Serialize
    List<Key<Recommendation>> recommendations = new ArrayList<Key<Recommendation>>();

    @Serialize
    List<Key<CheckIn>> checkIns = new ArrayList<Key<CheckIn>>();

    private List<String> categories = new ArrayList<String>();

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

    public void setRecommendations(List<Key<Recommendation>> recommendations) {
        this.recommendations = recommendations;
    }

    public List<Key<Recommendation>> getRecommendations() {
        return recommendations;
    }

    public void setCheckIns(List<Key<CheckIn>> checkIns) {
        this.checkIns = checkIns;
    }

    public List<Key<CheckIn>> getCheckIns() {
        return checkIns;
    }

    public void setFriends(List<Key<User>> friends) {
        this.friends = friends;
    }

    public List<Key<User>> getFriends() {
        return friends;
    }
}
