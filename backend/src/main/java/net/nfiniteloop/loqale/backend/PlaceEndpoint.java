package net.nfiniteloop.loqale.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.GeoPt;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

/**
 * Created by vaek on 11/16/14.
 */
@Api(name = "places", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.loqale.nfiniteloop.net", ownerName = "backend.loqale.nfiniteloop.net", packagePath=""))
public class PlaceEndpoint {
    private static final Logger log = Logger.getLogger(PlaceEndpoint.class.getName());

    /**
     * Return a collection of registered devices
     *
     * @param count The number of devices to list
     * @param userLongitude The coordinate of user device
     * @param userLatitude  The coordinate of user deivce
     * @param radiusInMeters Proximity to search
     * @return a list of Google Cloud Messaging registration Ids
     */
    @ApiMethod(name = "listPlaces")
    public Collection<Place> listPlaces(@Named("count") int count,
            @Named("longitude") Double userLongitude, @Named("latitude") Double userLatitude,
            @Named("range") Double radiusInMeters) {
        DataInjector injector = new DataInjector();
        injector.createDataSet();

        // Convert Strings into floats
        float longitude, latitude;
        longitude = userLongitude.floatValue();
        latitude = userLatitude.floatValue();
        GeoPt location = new GeoPt(latitude, longitude);

        List<Place> places = PlaceUtil.getPlacesByProximity(location,radiusInMeters,count);
        log.info(places.size() + " places returned ");

        return places;
        //return CollectionResponse.<Place>builder().setItems(places).build();
    }
}
