package net.nfiniteloop.loqale.backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Jama.Matrix;

import static net.nfiniteloop.loqale.backend.OfyService.ofy;

/**
 * Created by vaek on 10/5/14.
 */
public class Recommender extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private static final int placeQueryLimit = 100;
    private static final Logger log = Logger.getLogger(Recommender.class.getName());
    private final int DEFAULT_FAR_DISTANCE_MULTI = 4;

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
                        For each Tag (Future capability)
                            Get Label and query by label
                            return results by currency
            Filter Phase
                If Friendships
                    CF Friends to mark raw CheckIn recommendations
                    Repeat for Tags (future capability)
                If CheckIns
                    Build Levy Flight model and determine distance/frequency weights
                    Mark results with high distance/frequency weights

                If Tags (future capability)
                    Mark Results with high label frequency/or favorites
            Post Processing
                Generate Reason info for marked data
                Package marked recommendations
            Return recommendation package.
    */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId = req.getParameter("userID");
        // Filtering module
        ContentFilter contentFilter;
        // container to store matching places
        List<Place> refinedPlaces = new LinkedList<Place>();
        List<Place> similarPlaces = new LinkedList<Place>();
        List<Recommendation> filterResults = new ArrayList<Recommendation>();
        List<User> recUser = ofy().load().type(User.class).filter("userId", userId).list();
        // this query should never be empty. Might want to check though...
        List<CheckIn> userCheckIns = ofy().load().type(CheckIn.class).filter("userId", userId).list();
        if(userCheckIns.isEmpty()){
            // cold start logic
            // ok, so we don't know jack about this user, but we can send them a base set of
            // recommendations to jump start the engine and learn a bit about them. Of course this
            // totally depends on them actually liking the recommendations served in order to
            // provide some dimensions to work with
            // so what are the dimensions that are easily testable?
            //    size of business (corporate chain, or small business
            //    quality/cost business (cheap/expensive)
            //     distance (close/far)
            // these can be used in combinations (far small), (far, small, cheap)
            //
            // cold start algorithm:
            //    get the categories of interest to the new user
            //    define 2 ranges to test for distance based on user settings (d, and 4d)
            //    at each range, query for places that fall into each place category ( ala A/B testing)
            //    for range d look for places in same category, but with different qualities (exluding distance)
            //    pseudo
            //       for places in category
            //          place x cross place y = identity martrix
            //          add places to list
            //       invert matrix from  previous iteration
            //       for distance (3d < d < 4d), query locations by categories
            //       for places in category
            //          if place x cross place y = inverse identity martrix
            //             add place to list
            //    compile places and check for collisions (large business can have multiple sites)
            //    send out cold recommendations
            Double proximityLimit = recUser.get(0).getProximity();
            List<String> preferredCategories = new ArrayList<String>();
            similarPlaces =
                    PlaceUtil.getPlacesByProximity(recUser.get(0).getLocation(), proximityLimit, placeQueryLimit);
            // TODO: Filter places by category. Or better, move category filtering into ContentFilter
            //preferredCategories = recUser.get(0).getCategories();
            //for( String c : preferredCategories) {
            //    similarPlaces.addAll(ofy().load().type(Place.class).filter("category", c).list());
            //}
            ListIterator<Place> iter = similarPlaces.listIterator();
            while ( iter.hasNext() ) {
                Place singlePlace = iter.next();
                similarPlaces.remove(0);
                // for each place, use the quality relationships to sam ple places for A/B test
                if (iter.hasNext()) {
                    Matrix qualityMatrix = new Matrix(singlePlace.getQualityMatrix());
                    Matrix pMatrix = new Matrix(iter.next().getQualityMatrix());
                    pMatrix = pMatrix.times(qualityMatrix);
                    if (!qualityMatrix.equals(pMatrix.inverse())) {
                        refinedPlaces.add(iter.previous());
                        refinedPlaces.add(singlePlace);
                    }
                }
                // List<Place> refinedPlaces = new LinkedList<Place>();
            }
            contentFilter = new ContentFilter(recUser.get(0).getUserId(), refinedPlaces);
            filterResults = contentFilter.filter();
            ofy().save().entities(filterResults).now();
            // Redo process for ( proximityLimit * DEFAULT_FAR_DISTANCE_MULTIPLIER )
        }
        else {

            Set<String> categories = new HashSet<String>();
            // get the prefereded categories from the user profile. those will have higher weight
            // for each checkin, get the category using the placeId, and look for other places in range
            // of user home location
            // don't forget about recommendations that were liked! use that list to filter out
            // places that don't match on dimensional criteria
            for (CheckIn c : userCheckIns) {
                List<Place> checkInPlace = ofy().load().type(Place.class).filter("placeId", c.getPlaceId()).list();
                categories.add(checkInPlace.get(0).getCategory());

            }
            // first we handle the case where data exist in the db
            for (String s : categories) {
                // make sure you fix this! you could oull the whole db!
                similarPlaces.addAll(ofy().load().type(Place.class).filter("category", s).list());
            }

            // ok, got the categories. build a list of raw places
            // challenges:
            // i need to limit the query to locations within range of the
            // friends
            // Feed the list of places to the filter
            // Filter recFilter = new RecommendationFilter( similarPlaces );
            // rec filter removes places the user has checked in to and previous recommendations
            // refinedPlaces = recFilter.getResults()

            // friendfilter iterates over friends checkins and does a placeId count. looks for collisions and mark common ids
            // with higher weight
            // Filter FriendFilter = FriendFilter( refinedPlaces );
            // List recommendedPlaces = new LinkedList<Places>();
            // recommendedPlaces = refinedPlaces.getPlaces();

            // send list of recommended places to user
            ofy().save().entities(similarPlaces).now();
        }
        // send  filterResults the user

    }
}