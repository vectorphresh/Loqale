package net.nfiniteloop.loqale.backend;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by vaek on 10/5/14.
 */
@Entity
public class Place  {
    @Id
    private Long id;
    @Index
    private String placeId;
    private String name;
    private String address;
    private GeoPt location;

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
