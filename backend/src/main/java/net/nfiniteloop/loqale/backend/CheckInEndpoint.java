package net.nfiniteloop.loqale.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.sun.javafx.tk.Toolkit;

import java.util.List;
import java.util.Queue;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.jws.soap.SOAPBinding;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;
import static net.nfiniteloop.loqale.backend.OfyService.ofy;
/**
 * Created by vaek on 11/16/14.
 */
@Api(name = "checkins", version = "v1", namespace =
    @ApiNamespace(ownerDomain = "backend.loqale.nfiniteloop.net",
            ownerName = "backend.loqale.nfiniteloop.net", packagePath=""))
public class CheckInEndpoint {
    private static final Logger log = Logger.getLogger(RegistrationEndpoint.class.getName());

    /**
     * CheckIn to a place
     *
     */
    @ApiMethod(name = "checkin")
    public void checkIn( CheckIn checkInItem) {
        ofy().save().entity(checkInItem).now();
        Place checkInPlace;
        checkInPlace =
                ofy().load().type(Place.class)
                        .filter("placeId", checkInItem.getPlaceId()).list().get(0);
        User checkedInUser =
                ofy().load().type(User.class)
                        .filter("placeId", checkInItem.getUserId()).list().get(0);

        TagUtil.recordEvent(2, "Checked In at " + checkInPlace.getName(), checkedInUser);
        UpdateRecommendations(checkInItem);
    }

    /**
     * Return a collection of registered devices
     *
     * @param count The number of devices to list
     * @return a list of Google Cloud Messaging registration Ids
     */
    @ApiMethod(name = "listcheckins")
    public CollectionResponse<CheckIn> listDevices(@Named("count") int count, User user) {
        List<CheckIn> checkIns =
                ofy().load().type(CheckIn.class).limit(count)
                        .filter("userId", user.getUserId()).list();
        return CollectionResponse.<CheckIn>builder().setItems(checkIns).build();
    }

    private void UpdateRecommendations(CheckIn checkInItem) {
        log.info("passing to recommender");
        com.google.appengine.api.taskqueue.Queue queue = QueueFactory.getDefaultQueue();

        try {
            queue.add(withUrl("/recommend").param("userId", checkInItem.getUserId()));
        }
        catch(RuntimeException e){
            log.severe("something went wrong");

        }

    }

}