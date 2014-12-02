package net.nfiniteloop.loqale.backend;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by vaek on 11/2/14.
 */
public class ContentFilter implements Filter {
    public ContentFilter(String userId, List<Place> similarPlaces) {
        setUserId(userId);
        mPlaceList = new ArrayList<Place>(similarPlaces);
    }

    @Override
    public List<Recommendation> filter() {
        int count = 0;
        List<Recommendation> returnList = new ArrayList<Recommendation>();
        while( count != 10 ){
            for (Place p : mPlaceList){
                Recommendation newRec = new Recommendation();
                newRec.setContentId(UUID.randomUUID().toString());
                newRec.setRecommendationType(RecommendationType.PLACE);
                newRec.setContentId(p.getPlaceId());
                newRec.setDate(new Date(System.currentTimeMillis()));
                returnList.add(newRec);
            }
        }
        // Filter will remove elements that are not distance similar to previous check-ins
        // find hubs

        // compute distance from hubs

        // for each check-in id, compute the lowest distance to a hub,  consider temporal sequence

        // with the hub data computed process m

        return returnList;
    }

    @Override
    public void setUserId(String userId) {
        mUserId = userId;
    }

    private String mUserId;
    private List<Place> mPlaceList;
}
