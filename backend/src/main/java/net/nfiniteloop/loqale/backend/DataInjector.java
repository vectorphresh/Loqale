package net.nfiniteloop.loqale.backend;

import static net.nfiniteloop.loqale.backend.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by vaek on 12/8/14.
 */
public class DataInjector {
    // inject self id
    public DataInjector () {

    }
    double[][] cheapClose = new double[][]{
        {1.0, 0.0},
        {0.0, 0.0}
    };

    double[][] expensiveFar = new double[][]{
        {1.0, 0.0},
        {0.0, 1.0}
    };

    double[][] expensiveClose = new double[][]{
            {1.0, 0.0},
            {0.0, 1.0}
    };

    double[][] cheapFar = new double[][]{
            {0.0, 0.0},
            {1.0, 0.0}
    };

    double[][] off = new double[][]{
            {0.0, 0.0},
            {0.0, 0.0}
    };

    public Boolean createDataSet() {
        User userMitch = new User();
        userMitch.setDisplayName("Mitch");
        userMitch.setHometown("Torrance");
        userMitch.setProximity(2000.0);
        userMitch.setUserId("vaektor.phresh@gmail.com");
        // preference categories
        List<String> categories = new ArrayList<String>();
        categories.add("GAS");
        categories.add("BAR");
        categories.add("CFE");
        categories.add("FOD");
        userMitch.setCategories(categories);
        if (findUserRecord(userMitch) == null) {
            ofy().save().entity(userMitch).now();
        }
        // inject places

        Place place = new Place();
        place.setAddress("3960 Artesia Blvd, Torrance, CA 90504");
        place.setLatitude(33.8714422);
        place.setLongitude(-118.3445953);
        place.setCategory("GAS");
        place.setPlaceId("1");
        place.setQualityMatrix(cheapClose);
        place.setName("Chevron");
        if (findPlaceRecord(place) == null) {
            ofy().save().entity(place).now();
        }
        Place place2 = new Place();
        place2.setAddress("1400 W 190th St #F, Torrance, CA 90501");
        place2.setLatitude(33.8583);
        place2.setLongitude(-118.3021);
        place2.setCategory("CFE");
        place2.setPlaceId("2");
        place2.setQualityMatrix(cheapClose);
        place2.setName("Starbucks");
        if (findPlaceRecord(place2) == null) {
            ofy().save().entity(place2).now();
        }
        Place place3 = new Place();
        place3.setAddress("1413 Hawthorne Blvd, Redondo Beach CA, 90278");
        place3.setLatitude(33.867005);
        place3.setLongitude(-118.353856);
        place3.setCategory("GRC");
        place3.setPlaceId("3");
        place3.setQualityMatrix(cheapClose);
        place3.setName("Ralph's");
        if (findPlaceRecord(place3) == null) {
            ofy().save().entity(place3).now();
        }


        Place place4 = new Place();
        place4.setAddress("1000 East Victoria St, Carson, CA 90504");
        place4.setLatitude(33.8671);
        place4.setLongitude(-118.2586);
        place4.setCategory("EDU");
        place4.setPlaceId("4");
        place4.setQualityMatrix(off);
        place4.setName("Cal State Dominguez Hills");
        if (findPlaceRecord(place4) == null) {
            ofy().save().entity(place4).now();
        }

        Place place5 = new Place();
        place5.setAddress("20240 Avalon Blvd, Carson, CA 90746");
        place5.setLatitude(33.83952);
        place5.setLongitude(-118.2471891);
        place5.setCategory("CFE");
        place5.setPlaceId("5");
        place5.setQualityMatrix(cheapFar);
        place5.setName("Starbucks");
        if (findPlaceRecord(place5) == null) {
            ofy().save().entity(place5).now();
        }

        Place place6 = new Place();
        place6.setAddress("17453 S Central Ave,Carson, CA 90746");
        place6.setLatitude(33.83952);
        place6.setLongitude(-118.2471891);
        place6.setCategory("GAS");
        place6.setPlaceId("6");
        place6.setQualityMatrix(cheapClose);
        place6.setName("Chevron");
        if (findPlaceRecord(place6) == null) {
            ofy().save().entity(place6).now();
        }

        Place place7 = new Place();
        place7.setAddress("1515 Hawthorne Blvd, Redondo Beach, CA 90278");
        place7.setLatitude(33.854634);
        place7.setLongitude(-118.3771421);
        place7.setCategory("GRC");
        place7.setPlaceId("7");
        place7.setQualityMatrix(cheapClose);
        place7.setName("Sprouts Farmers Market");
        if (findPlaceRecord(place7) == null) {
            ofy().save().entity(place7).now();
        }

        Place place8 = new Place();
        place8.setAddress("20930 Hawthorne Blvd, Torrance, CA 90503");
        place8.setLatitude(33.839543);
        place8.setLongitude(-118.353008);
        place8.setCategory("BAR");
        place8.setPlaceId("8");
        place8.setQualityMatrix(expensiveClose);
        place8.setName("Zebra Room");
        if (findPlaceRecord(place8) == null) {
            ofy().save().entity(place8).now();
        }

        Place place9 = new Place();
        place9.setAddress("18200 Hawthorne Blvd, Torrance, CA 9050");
        place9.setLatitude(33.865056);
        place9.setLongitude(-118.3522);
        place9.setCategory("FOD");
        place9.setPlaceId("9");
        place9.setQualityMatrix(cheapClose);
        place9.setName("Chick-fil-A");
        if (findPlaceRecord(place9) == null) {
            ofy().save().entity(place9).now();
        }

        Place place10 = new Place();
        place10.setAddress("17916 Hawthorne Blvd, Torrance, CA 90504");
        place10.setLatitude(33.8336389);
        place10.setLongitude(-118.3511089);
        place10.setCategory("FOD");
        place10.setPlaceId("10");
        place10.setQualityMatrix(cheapClose);
        place10.setName("Jack In the Box");
        if (findPlaceRecord(place10) == null) {
            ofy().save().entity(place10).now();
        }

        Place place11 = new Place();
        place11.setAddress("17916 Hawthorne Blvd, Torrance, CA 90504");
        place11.setLatitude(33.873067);
        place11.setLongitude(-118.37438);
        place11.setCategory("FOD");
        place11.setPlaceId("11");
        place11.setQualityMatrix(cheapClose);
        place11.setName("Papa John's Pizza");
        if (findPlaceRecord(place11) == null) {
            ofy().save().entity(place11).now();
        }
        return true;
    }
    private User findUserRecord(User record ) {
        return ofy().load().type(record.getClass()).filter("userId", record.getUserId()).first().now();
    }
    private Place findPlaceRecord(Place record ) {
        return ofy().load().type(record.getClass()).filter("placeId", record.getPlaceId()).first().now();
    }
}
