package net.nfiniteloop.loqale.backend;

import com.google.appengine.api.datastore.GeoPt;

import java.util.LinkedList;
import java.util.List;
import static net.nfiniteloop.loqale.backend.OfyService.ofy;
/**
 * Created by vaek on 12/1/14.
 */
public class PlaceUtil {
    final static int radiusEarthMeters = 6378137;
    public static List<Place> getPlacesByProximity(GeoPt origin, Double proximityMeters){
        List<Place> places = new LinkedList<Place>();
        double deltaLat = (float) proximityMeters.doubleValue() / radiusEarthMeters;
        double deltaLon =
                (float) proximityMeters.doubleValue() / ( radiusEarthMeters * Math.cos(Math.toRadians(origin.getLongitude())) );
        float originLat = origin.getLatitude();
        float originLon = origin.getLongitude();

        //query objectify
        places.addAll(ofy().load().type(Place.class).filter("latitude >=", originLat + deltaLat )
                .filter("latitude <=", originLat - deltaLat )
                .filter("longitude >=", originLon + deltaLon)
                .filter("longitude <=", originLon - deltaLon).limit(100).list());

        return places;
    }

    public double getDistanceInMeters(GeoPt location1, GeoPt location2){

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
