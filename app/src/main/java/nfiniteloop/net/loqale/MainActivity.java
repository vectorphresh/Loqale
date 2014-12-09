package nfiniteloop.net.loqale;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener{
    // Logging for debugging
    private Logger log = Logger.getLogger(MainActivity.class.getName());
    LoqalePageAdapter pageAdapter;
    ViewPager pager;

    //container class for places fragment
    private ArrayList<MessageItem> messages;
    private ArrayList<PlaceItem> places;


    Registration registrationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //List<Fragment> fragments = getFragments();
        messages = new ArrayList<MessageItem>();
        places = new ArrayList<PlaceItem>();

       // pager = new ViewPager(this);
       // pager.setId(R.id.viewpager);
        //pageAdapter = new LoqalePageAdapter(getApplicationContext(),getSupportFragmentManager(), fragments);
        //pager.setAdapter(pageAdapter);
        final ActionBar actionbar = getActionBar();
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // [END Citation]
        actionbar.addTab(actionbar.newTab().setText(R.string.title_messages).setTabListener(this));
        actionbar.addTab(actionbar.newTab().setText(R.string.title_places).setTabListener(this));
        //actionbar.addTab(actionbar.newTab().setText(R.string.title_recommendation).setTabListener(this));
        //  Lets check if were dealing with a new user

        SharedPreferences prefs = getSharedPreferences(LoqaleConstants.PREFS_NAME, Context.MODE_PRIVATE);
        Boolean newbie = prefs.getBoolean("newUser", true);
        String userDevice = prefs.getString("deviceId", "");
        String userName = prefs.getString("username", "Anon Loqal");
        int proximity = prefs.getInt("proximity", 1000);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void onSectionAttached(int number) {

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
        return super.onOptionsItemSelected(item);
    }

    private void showSettingsActivity() {
        Intent intent;
        intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private List<Fragment> getFragments() {
        List<Fragment> listFragments = new ArrayList<Fragment>();
        listFragments.add((MessageFragment.newInstance(messages)));
        listFragments.add(PlaceFragment.newInstance(places));
        return listFragments;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        if (tab.getPosition() == 0) {
            MessageFragment messageFragment = MessageFragment.newInstance(messages);
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout, messageFragment).commit();
        }
        else if (tab.getPosition() == 1) {
            PlaceFragment placeFragment = PlaceFragment.newInstance(places);
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout, placeFragment).commit();
        }

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
