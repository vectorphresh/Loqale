package nfiniteloop.net.loqale;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import net.nfiniteloop.loqale.backend.checkins.Checkins;
import net.nfiniteloop.loqale.backend.checkins.model.User;
import net.nfiniteloop.loqale.backend.places.Places;
import net.nfiniteloop.loqale.backend.places.model.Place;
import net.nfiniteloop.loqale.backend.recommendations.Recommendations;
import net.nfiniteloop.loqale.backend.registration.Registration;
import net.nfiniteloop.loqale.backend.registration.model.RegistrationRecord;
import net.nfiniteloop.loqale.backend.registration.model.RegistrationRecordCollection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener{
    // Logging for debugging
    private Logger log = Logger.getLogger(MainActivity.class.getName());

    // asynchronous support classes
    private static LoqaleFeedGetter feedTask;
    private static RecommendationGetter recommendationTask;
    private static placeGetter placesTask;
    private static checkInHelper checkInsTask;

    // endpoint client services
    private static Checkins checkInService;
    private static Places placesService;
    private static Recommendations recommendationService;

    //container class for places fragment
    private TextView placesView;
    private ListView placesList;
    private List<Place> places = null;


    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    Registration registrationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // First we'll set up our actionbar and layout elements
        ActionBar actionbar = getActionBar();
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // [BEGIN Source Citation] From Google Android Documentation
        // https://developer.android.com/guide/topics/ui/actionbar.html
        // TODO: Add the remaining fragments
        // I'm going to be lazy I think and implement a places fragment that I can overload for
        // the recommendation tab. I will hide the like(heart) button on the recommendations tab.
        // 12/01/14: Yep Laziness wins! We'll extend the current fragment/adapter to support all
        // tabs.
        ActionBar.Tab feedTab = actionbar.newTab()
                .setText(R.string.title_messages)
                .setTabListener(new TabListener<MessageFragment> (
                        this, "feed", MessageFragment.class));
        actionbar.addTab(feedTab);

        ActionBar.Tab placesTab = actionbar.newTab()
                .setText(R.string.title_places)
                .setTabListener(new TabListener<PlaceFragment> (
                        this, "places", PlaceFragment.class));
        actionbar.addTab(placesTab);
        /*
        ActionBar.Tab recommendationTab = actionbar.newTab()
                .setText(R.string.title_recommendation)
                .setTabListener(new TabListener<LoqaleFragment> (
                        this, "recommendation", LoqaleFragment.class));
        actionbar.addTab(recommendationTab);
        */
        // [END Citation]
        //actionbar.addTab(actionbar.newTab().setText(R.string.title_messages).setTabListener(this));
        //actionbar.addTab(actionbar.newTab().setText(R.string.title_places).setTabListener(this));
        //actionbar.addTab(actionbar.newTab().setText(R.string.title_recommendation).setTabListener(this));
        //  Lets check if were dealing with a new user

        SharedPreferences prefs = getSharedPreferences(LoqaleConstants.PREFS_NAME, Context.MODE_PRIVATE);
        Boolean newbie = prefs.getBoolean("newUser", true);
        String userDevice = prefs.getString("deviceId", "");
        String userName = prefs.getString("username", "Anon Loqal");
        int proximity = prefs.getInt("proximity", 1000);

            if(newbie){
            // add welcome message to main display
            //
            //
            // push the users default credentials to the server
            User newbieUser = new User();


        }
        else {

        }

        mTitle = getTitle();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void onSectionAttached(int number) {

    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            showSettingsActivity();
        }
        log.severe("804");
        return super.onOptionsItemSelected(item);
    }

    private void showSettingsActivity() {
        Intent intent;
        intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // get the selected tab
        log.severe("test");
        feedTask = new LoqaleFeedGetter();
        feedTask.execute((Void) null);

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        log.severe("test");

    }

    public class LoqaleFeedGetter extends AsyncTask<Void, Void, Boolean> {

        List<RegistrationRecord> foo = new ArrayList<RegistrationRecord>();
        MessageItem bar;
        @Override
        protected Boolean doInBackground(Void... params) {
            if (registrationService == null) { // Only do this once
                Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(),
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
                log.info("Do I get here?");
                // end options for devappserver
                registrationService = builder.build();
            }
            try {
                RegistrationRecordCollection ugh = registrationService.listDevices(1).execute();
                foo.addAll(ugh.getItems());

                if(!foo.isEmpty()) {
                    bar.message = foo.get(0).getRegId();
                    bar.username= "Mitch";
                    int imgR = R.drawable.ic_local_bar_black_36dp;
                    bar.pic = getResources().getDrawable(imgR);
                    log.info("device"+ foo.get(0).getRegId());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(success){
                //getActionBar().getSelectedTab().getCustomView().
            }
        }

        @Override
        protected void onCancelled() {

        }
    }

    public class placeGetter extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

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
                placesService = builder.build();
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }

        @Override
        protected void onCancelled() {

        }
    }

    public class checkInHelper extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            if (checkInService == null) { // Only do this once
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
                checkInService = builder.build();
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }

        @Override
        protected void onCancelled() {

        }
    }

    public class RecommendationGetter extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            if (recommendationService == null) { // Only do this once
                Recommendations.Builder builder = new Recommendations.Builder(AndroidHttp.newCompatibleTransport(),
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
                recommendationService = builder.build();
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }

        @Override
        protected void onCancelled() {

        }
    }


}
