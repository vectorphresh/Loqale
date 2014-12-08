package nfiniteloop.net.loqale;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import net.nfiniteloop.loqale.backend.places.Places;
import net.nfiniteloop.loqale.backend.places.model.Place;
import net.nfiniteloop.loqale.backend.places.model.PlaceCollection;
import net.nfiniteloop.loqale.backend.registration.Registration;
import net.nfiniteloop.loqale.backend.registration.model.RegistrationRecord;
import net.nfiniteloop.loqale.backend.registration.model.RegistrationRecordCollection;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * A fragment representing a list of Items.
 * no listener, since were just displaying static items
 */
public class PlaceFragment extends ListFragment {
    private Logger log = Logger.getLogger(PlaceFragment.class.getName());
    private PlaceAdapter adapter;
    Places placesService;
    private WeakReference<PlaceGetterTask> asyncTaskWeakRef;

    //test harness containers
    // TODO: Implement real location gatherer.

    public static PlaceFragment newInstance(ArrayList<PlaceItem> items) {
        PlaceFragment mf = new PlaceFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("items", items);
        bundle.putString("longitude", "-118.3503218");
        bundle.putString("latitude", "33.8640103");
        mf.setArguments(bundle);

        return mf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //items.add((PlaceItem) savedInstanceState.getParcelableArrayList("messages").get(0));
        setRetainInstance(true);
        PlaceGetterTask mg = new PlaceGetterTask(this);
        this.asyncTaskWeakRef = new WeakReference<PlaceGetterTask>(mg);
        mg.execute();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // select an layout to inflate
        View view = inflater.inflate(R.layout.loqale_messages,container,false);

        return view;
    }

    public int categoryToIconId(String category) {
        Map<String, Integer> categoryPicKeys = new HashMap<String, Integer>();
        categoryPicKeys.put("GAS", R.drawable.ic_local_gas_station_black_36dp );
        categoryPicKeys.put("BAR", R.drawable.ic_local_bar_black_36dp);
        categoryPicKeys.put("CFE", R.drawable.ic_local_cafe_black_36dp );
        categoryPicKeys.put("GRC", R.drawable.ic_local_grocery_store_black_36dp );
        categoryPicKeys.put("FOD", R.drawable.ic_local_pizza_black_36dp );
        categoryPicKeys.put("SCH", R.drawable.ic_school_black_36dp );
        categoryPicKeys.put("RST", R.drawable.ic_restaurant_menu_black_36dp );

        return categoryPicKeys.get(category);
    }

    public class PlaceGetterTask extends AsyncTask<Void, Void, ArrayList<PlaceItem> > {

        List<Place> foo = new ArrayList<Place>();
        ArrayList<PlaceItem> bar = new ArrayList<PlaceItem>();
        private WeakReference<PlaceFragment> fragmentWeakRef;
        private Double longitude, latitude;

        private PlaceGetterTask(PlaceFragment fragment) {
            this.fragmentWeakRef = new WeakReference<PlaceFragment>(fragment);
        }
        // must call this first!!
        public void setLocation(Double Latitude, Double Longitude) {
            latitude = Latitude;
            longitude = Longitude;
        }
        @Override
        protected ArrayList<PlaceItem> doInBackground(Void... params) {
            if (placesService == null) { // Only do this once
                Places.Builder builder = new Places.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver
                placesService = builder.build();
            }
            try {
                int count=10;
                Double range =1000.0;
                PlaceCollection ugh = placesService.
                        listPlaces(count, longitude, latitude, range).execute();
                foo.addAll(ugh.getItems());

                if(!foo.isEmpty()) {
                    for( Place p : foo) {
                        PlaceItem mi = new PlaceItem();
                        mi.setPlaceName(p.getName());
                        mi.setLongitude(p.getLongitude());
                        mi.setLatitude(p.getLatitude());
                        mi.setDistance(p.getDistance());
                        // we need a map here!

                        mi.setPicCategory(this.fragmentWeakRef.get()
                                .categoryToIconId(p.getCategory()));
                        bar.add(mi);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bar;
        }

        @Override
        protected void onPostExecute(final ArrayList<PlaceItem> result) {
            if (this.fragmentWeakRef.get() != null) {
                adapter = new PlaceAdapter(getActivity(), result);
                setListAdapter(adapter);
            }

        }
    }



}
