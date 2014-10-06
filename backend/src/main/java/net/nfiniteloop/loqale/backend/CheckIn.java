package net.nfiniteloop.loqale.backend;

import com.google.appengine.api.datastore.Key;
import com.googlecode.objectify.annotation.Entity;

import java.util.Date;

/**
 * Created by vaek on 10/5/14.
 */
@Entity
public class CheckIn {
    private Key key;
    private String userId;
    private String placeId;
    private int rating;
    private Date checkInDate;

    public Key getKey() {
        return key;
    }

    public String getUserId() {
        return userId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public int getRating() {
        return rating;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }
}
