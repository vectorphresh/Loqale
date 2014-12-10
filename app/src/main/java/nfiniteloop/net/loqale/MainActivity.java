package nfiniteloop.net.loqale;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
    // Logging for debugging
    private Logger log = Logger.getLogger(MainActivity.class.getName());

    //container class for places fragment
    private ArrayList<MessageItem> mMessages;
    private ArrayList<PlaceItem> mPlaces;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mMessages = new ArrayList<MessageItem>();
        mPlaces = new ArrayList<PlaceItem>();

        final ActionBar actionbar = getActionBar();
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionbar.addTab(actionbar.newTab().setText(R.string.title_messages).setTabListener(this));
        actionbar.addTab(actionbar.newTab().setText(R.string.title_places).setTabListener(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
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

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        if (tab.getPosition() == 0) {
            MessageFragment messageFragment = MessageFragment.newInstance(mMessages);
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout, messageFragment).commit();
        } else if (tab.getPosition() == 1) {
            PlaceFragment placeFragment = PlaceFragment.newInstance(mPlaces);
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
