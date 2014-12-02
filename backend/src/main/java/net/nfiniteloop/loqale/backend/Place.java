package net.nfiniteloop.loqale.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Serialize;

/**
 * Created by vaek on 10/5/14.
 */
@Entity
public class Place {
    @Id
    private Long id;
    @Index
    private String placeId;
    private String name;
    private String address;
    private Double longitude;
    private Double latitude;
    private String category;

    @Serialize
    private double[][] qualityMatrix;

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public String getPlaceId(){
        return placeId;
    }

    public String getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQualityMatrix(double[][] qualityMatrix) {
        this.qualityMatrix = qualityMatrix;
    }

    public double[][] getQualityMatrix() {
        return qualityMatrix;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
