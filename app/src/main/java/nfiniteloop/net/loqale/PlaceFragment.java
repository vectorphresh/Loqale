package nfiniteloop.net.loqale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.util.DateTime;

import net.nfiniteloop.loqale.backend.checkins.Checkins;
import net.nfiniteloop.loqale.backend.checkins.model.CheckIn;
import net.nfiniteloop.loqale.backend.places.Places;
import net.nfiniteloop.loqale.backend.places.model.Place;
import net.nfiniteloop.loqale.backend.places.model.PlaceCollection;
import net.nfiniteloop.loqale.backend.registration.Registration;
import net.nfiniteloop.loqale.backend.registration.model.RegistrationRecord;
import net.nfiniteloop.loqale.backend.registration.model.RegistrationRecordCollection;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
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
    Checkins checkinsService;
    private Boolean dialogResult;
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

    @Override
    public void onListItemClick(ListView l, View v, int i, long id) {
        // stackOverflow
        // https://stackoverflow.com/questions/2478517/how-to-display-a-yes-no-dialog-box-in-android
        dialogResult = false;
        CheckInHelperTask ck = new CheckInHelperTask(this);
        super.onListItemClick(l, v, i, id);

        PlaceItem selectedItem;
        selectedItem = (PlaceItem) l.getItemAtPosition(i);

        Boolean result = CheckInDialog(selectedItem.getPlaceName());
        CheckIn placeCheckIn = new CheckIn();
        SharedPreferences prefs = getActivity()
                .getSharedPreferences(LoqaleConstants.PREFS_NAME, Context.MODE_PRIVATE);
        String userDevice = prefs.getString("deviceId", "");
        log.info("we get here");
        if(!userDevice.isEmpty()) {
            log.info("but how about here?");
            placeCheckIn.setUserId(userDevice);
            placeCheckIn.setCheckInDate(new DateTime(System.currentTimeMillis()));
            placeCheckIn.setPlaceId(selectedItem.getPlaceId());

            ck.execute(placeCheckIn);
        }
    }

    private Boolean CheckInDialog(String placeName) {
        // stackOverflow
        // https://stackoverflow.com/questions/2478517/how-to-display-a-yes-no-dialog-box-in-android
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Checking In at " + placeName + "?" );
        builder.setMessage("Would you like to check in?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialogResult = true;
                dialog.dismiss();
            }

        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

        return dialogResult;

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
        private Double longitude = new Double(-118.3503218);
        private Double latitude = new Double(33.8640103);

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
                Double range = new Double(1000.0);
                PlaceCollection ugh = placesService.
                        listPlaces(count, longitude, latitude, range).execute();
                if(!ugh.isEmpty()) {
                    log.info(ugh.size() +" places returned");
                    foo.addAll(ugh.getItems());
                    for (Place p : foo) {
                        PlaceItem pi = new PlaceItem();
                        pi.setPlaceName(p.getName());
                        pi.setLongitude(p.getLongitude());
                        pi.setLatitude(p.getLatitude());
                        pi.setDistance(p.getDistance());
                        pi.setPlaceId(p.getPlaceId());
                        // we need a map here!
                        int picId = this.fragmentWeakRef.get()
                                .categoryToIconId(p.getCategory());
                        pi.setPicCategory(getResources().getDrawable(picId));
                        bar.add(pi);
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

    public class CheckInHelperTask extends AsyncTask<CheckIn, Void, Boolean> {

        List<RegistrationRecord> foo = new ArrayList<RegistrationRecord>();
        ArrayList<MessageItem> bar = new ArrayList<MessageItem>();
        private WeakReference<PlaceFragment> fragmentWeakRef;

        private CheckInHelperTask (PlaceFragment fragment) {
            this.fragmentWeakRef = new WeakReference<PlaceFragment>(fragment);
        }

        @Override
        protected Boolean doInBackground(CheckIn... params) {
            if (checkinsService == null) { // Only do this once
                Checkins.Builder builder = new Checkins.Builder(AndroidHttp.newCompatibleTransport(),
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
                checkinsService = builder.build();
            }
            try {
                CheckIn checkInItem = params[0];
                log.info("Testing...");
                checkinsService.checkin(checkInItem).execute();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                // TODO: Pop a dialog
            }

        }
    }

}
