package nfiniteloop.net.loqale;

import android.graphics.drawable.Drawable;

/**
 * Created by vaek on 12/6/14.
 */
public class RecommendItem {
    Drawable picCategory;
    String placeName;
    String placeDistance;
    Drawable picLike;

    public RecommendItem(Drawable pic, String name, String distance, Drawable likepic) {
        this.picCategory = pic;
        this.placeName = name;
        this.placeDistance = distance;
        this.picLike = likepic;

    }
}
