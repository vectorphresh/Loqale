package nfiniteloop.net.loqale;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vaek on 12/6/14.
 */
public class PlaceItem implements Parcelable {
    private int picCategory;
    private String placeName;
    private Double longitude;
    private Double latitude;
    private Double distance;

    public PlaceItem() {}

    public PlaceItem(int pic, String name, Double latitude, Double longitude, Double distance) {
        this.picCategory = pic;
        this.placeName = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(picCategory);
        parcel.writeString(placeName);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeDouble(distance);
    }

    public void setPicCategory(int picCategory) {
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

    public int getPicCategory() {
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

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}

