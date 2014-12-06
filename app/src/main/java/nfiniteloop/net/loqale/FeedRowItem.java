package nfiniteloop.net.loqale;

import android.graphics.drawable.Drawable;

/**
 * Created by vaek on 12/1/14.
 */
public class FeedRowItem {
    Drawable pic;
    String username;
    String message;

    public FeedRowItem(Drawable pic, String username, String message) {
        this.pic = pic;
        this.username = username;
        this.message = message;
    }

}
