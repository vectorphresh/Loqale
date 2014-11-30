package net.nfiniteloop.loqale.backend;

import com.google.appengine.api.datastore.GeoPt;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static net.nfiniteloop.loqale.backend.OfyService.ofy;

/**
 * Created by vaek on 10/5/14.
 */
public class Recommender extends HttpServlet{

    private final int RAW_LIMIT = 100;


    /* Working thoughts
          Think Thread Safety!!
          Think runtime!!
          Algorithm
            Build User Profile
            // get check ins
            Raw Recommendation Phase
                Is this a Cold Start?
                    Yes: Use Levy Flight Pattern to get raw recommendations
                        For each point on Path
                            Get establishment by nearest location and query by category
                            Return weighted results for category
                                (Make sure to handle collisions)

                    No: Use Profile to get raw recommendations
                        For each CheckIn
                            Get establishments category and query by category
                            Return weighted results for category
                        For each Tag
                            Get Label and query by label
                            return results by currency
            Filter Phase
                If Friendships
                    CF Friends to mark raw CheckIn recommendations
                    Repeat for Tags
                If CheckIns
                    Build Levy Flight model and determine distance/frequency weights
                    Mark results with high distance/frequency weights

                If Tags
                    Mark Results with high label frequency/or favorites
            Post Processing
                Generate Reason info for marked data
                Package marked recommendations
            Return recommendation package.
    */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId = req.getParameter("userID");
        Profile userProfile = new Profile();

        List<User> recUser = ofy().load().type(User.class).filter("userId", userId).list();
        List<CheckIn> userCheckIns = ofy().load().type(CheckIn.class).filter("userId", userId).list();

        // container to store matching places
        List<Place> similarPlaces = new LinkedList<Place>();
        Set<String> categories = new HashSet<String>();
        // for each checkin, get the category using the placeId, and look for other places in range
        // of user home location
        for( CheckIn c : userCheckIns ){
            List<Place> checkInPlace = ofy().load().type(Place.class).filter("placeId", c.getPlaceId()).list();
            categories.add(checkInPlace.get(0).getCategory());

        }
        // first we handle the case where data exist in the db
        for(String s : categories){
            // make sure you fix this! you could oull the whole db!
            similarPlaces.addAll( ofy().load().type(Place.class).filter("category", s).list() );
        }
        // two phases need to be accomplished here
        // Feed the list of places to the filter
        // Filter myfilter = new Filter( similarPlaces );
        //List refinedPlaces = new LinkedList<Places>()
        //myFilter.getResults()

        // ok, got the categories. build a list of raw places
        // challenges:
        // i need to limit the query to locations within range of the





    }
}