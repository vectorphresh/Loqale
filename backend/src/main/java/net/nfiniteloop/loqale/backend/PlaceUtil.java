package net.nfiniteloop.loqale.backend;

import com.google.appengine.api.datastore.GeoPt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static net.nfiniteloop.loqale.backend.OfyService.ofy;
/**
 * Created by vaek on 12/1/14.
 */


public class PlaceUtil {
    final static int radiusEarthMeters = 6378137;
    final static double metersToMiles = 0.000621371;

    public static List<Place> getPlacesByProximity(GeoPt origin, Double proximityMeters, int count){
        List<Place> places = new ArrayList<Place>();
        double deltaLat = (float) proximityMeters.doubleValue() / radiusEarthMeters;
        double deltaLon =
                (float) proximityMeters.doubleValue() / ( radiusEarthMeters * Math.cos(Math.toRadians(origin.getLongitude())) );
        float originLat = origin.getLatitude();
        float originLon = origin.getLongitude();

        //hmmm... inequality filters are limited to one operation
        // this would be expensive on a large DB...
        List<Place> results = new ArrayList<Place>();
        places.addAll(ofy().load().type(Place.class).limit(count).list());

        for(Iterator<Place> iter = places.iterator(); iter.hasNext();) {
            Place p = iter.next();
            GeoPt placeLocation = new GeoPt(p.getLatitude().floatValue(), p.getLongitude().floatValue());
            double distance = PlaceUtil.getDistanceInMeters(origin,placeLocation);
            if(distance <= proximityMeters){
                // covert distance to string and pack into payload for device
                double distanceMiles = distance * metersToMiles;
                String distanceStr = String.format("%.2f", distanceMiles);
                p.setDistance(distanceStr + " miles");
                results.add(p);
            }
        }
        return results;
    }

    public static double getDistanceInMeters(GeoPt location1, GeoPt location2){

        // derived from haversine
        double radiusEarth = 6378137; // meters
        double loc1Lat = Math.toRadians(location1.getLatitude());
        double loc2Lat = Math.toRadians(location2.getLatitude());
        double latDelta = Math.toRadians(location2.getLatitude()-location1.getLatitude());
        double lonDelta = Math.toRadians(location2.getLongitude() - location1.getLongitude());

        double a = Math.sin(latDelta/2) * Math.sin(latDelta/2) +
                Math.cos(loc1Lat) * Math.cos(loc2Lat) *
                        Math.sin(lonDelta/2) * Math.sin(lonDelta/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double distance = radiusEarth * c;

        return distance;
    }


}
