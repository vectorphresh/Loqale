package net.nfiniteloop.loqale.backend;

import static net.nfiniteloop.loqale.backend.OfyService.ofy;

/**
 * Created by vaek on 12/9/14.
 */
public class TagUtil {
    public static void recordEvent(int eventType, String eventMsg, User user) {
        Tag event = new Tag();
        // 1:user_event, 2:place_event, 3_recommendation_event
        event.setTagCategory(eventType);
        event.setText(eventMsg);
        event.setTagId(user.getUserId());
        ofy().save().entity(event).now();
    }

}
