package net.nfiniteloop.loqale.backend;

import java.util.List;

/**
 * Created by vaek on 10/21/14.
 *
 * Filter Interface class to support extending filtering functions in the future
 */
public interface Filter {

    /*
     * Default filter will determine by distance similarity with previous check-ins in the
     * user profile
     */
    public void filter( List<Recommendation> recommendationList);

    public void setProfile(Profile userProfile);


}
