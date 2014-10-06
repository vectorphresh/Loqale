package net.nfiniteloop.loqale.backend;

import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.Key;
import com.googlecode.objectify.annotation.Entity;

/**
 * Created by vaek on 10/5/14.
 */
@Entity
public class Place  {
    private Key key;
    private String name;
    private String address;
    private GeoPt location;
    private String placeId;

    public Key getKey(){
        return key;
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public GeoPt getLocation(){
        return location;
    }

    public String getPlaceId(){
        return placeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLocation(GeoPt location) {
        this.location = location;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
