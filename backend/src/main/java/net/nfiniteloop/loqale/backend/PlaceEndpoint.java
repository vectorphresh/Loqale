package net.nfiniteloop.loqale.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.GeoPt;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

/**
 * Created by vaek on 11/16/14.
 */
@Api(name = "places", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.loqale.nfiniteloop.net", ownerName = "backend.loqale.nfiniteloop.net", packagePath=""))
public class PlaceEndpoint {
    private static final Logger log = Logger.getLogger(RegistrationEndpoint.class.getName());

    /**
     * Return a collection of registered devices
     *
     * @param count The number of devices to list
     * @return a list of Google Cloud Messaging registration Ids
     */
    @ApiMethod(name = "listDevices")
    public CollectionResponse<Place> listPlaces(@Named("count") int count,
            @Named("longitude") String longStr, @Named("latitude") String latStr,
            @Named("range") String radiusInMiles) {

        // Convert Strings into floats
        float longitude, latitude;
        Double proximity;
        longitude = Float.parseFloat(longStr);
        latitude = Float.parseFloat(latStr);
        GeoPt location = new GeoPt(latitude, longitude);
        proximity = Double.parseDouble(radiusInMiles);

        List<Place> places = PlaceUtil.getPlacesByProximity(location,proximity,count);
        return CollectionResponse.<Place>builder().setItems(places).build();
    }
}
