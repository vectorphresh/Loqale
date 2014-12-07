package nfiniteloop.net.loqale;

import android.graphics.drawable.Drawable;

/**
 * Created by vaek on 12/6/14.
 */
public class PlaceItem {
    Drawable picCategory;
    String placeName;
    String placeDistance;

    public PlaceItem(Drawable pic, String name, String distance) {
        this.picCategory = pic;
        this.placeName = name;
        this.placeDistance = distance;
    }
}
