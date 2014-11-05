package net.nfiniteloop.loqale.backend;

import java.util.List;

/**
 * Created by vaek on 11/2/14.
 */
public class ContentFilter implements Filter {
    @Override
    public void filter(List<Recommendation> recommendationList) {
        // Filter will remove elements that are not distance similar to previous check-ins
        if( profile != null ){
            // compute distance from check-ins
            List<CheckIn> checkIns = profile.getCheckIns();
            // find hubs

            // compute distance from hubs

            // for each check-in id, compute the lowest distance to a hub,  consider temporal sequence

            // with the hub data computed process m

        }
        else {
            return;
        }
    }

    @Override
    public void setProfile(Profile userProfile) {
        profile = userProfile;
    }

    private Profile profile;
}
