package nfiniteloop.net.loqale;

import android.graphics.drawable.Drawable;

/**
 * Created by vaek on 12/1/14.
 */
public class MessageItem {
    Drawable pic;
    String username;
    String message;

    public MessageItem(Drawable pic, String username, String message) {
        this.pic = pic;
        this.username = username;
        this.message = message;
    }

}
