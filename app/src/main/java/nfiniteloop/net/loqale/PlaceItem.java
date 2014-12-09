package nfiniteloop.net.loqale;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vaek on 12/6/14.
 */
public class PlaceItem implements Parcelable {
    private Drawable picCategory;
    private String placeName;
    private Double longitude;
    private Double latitude;
    private String distance;
    private String placeId;

    public PlaceItem() {}

    public PlaceItem(Drawable pic, String name, Double latitude, Double longitude, String distance, String placeId) {
        this.picCategory = pic;
        this.placeName = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
        this.placeId = placeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(placeName);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(distance);
        parcel.writeString(placeId);
    }

    public void setPicCategory(Drawable picCategory) {
        this.picCategory = picCategory;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Drawable getPicCategory() {
        return picCategory;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceId() {
        return placeId;
    }
}

