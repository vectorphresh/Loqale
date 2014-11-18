package nfiniteloop.net.loqale;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import net.nfiniteloop.loqale.backend.CheckIn;
import net.nfiniteloop.loqale.backend.Place;
import net.nfiniteloop.loqale.backend.checkins.Checkins;
import net.nfiniteloop.loqale.backend.places.Places;
import net.nfiniteloop.loqale.backend.recommendation.Recommendation;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // insert main screen fragment
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    private class CheckInTask extends AsyncTask<Place, Void, Void> {

        /**
         * Calls appropriate CloudEndpoint to indicate that user checked into a place.
         *
         * @param params the place where the user is checking in.
         */
        @Override
        protected Void doInBackground(Place... params) {

            CheckIn checkin = new CheckIn();
            checkin.setPlaceId(params[0].getPlaceId());

            //Builder endpointBuilder = new Shoppingassistant.Builder(
            //        AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
            //        CloudEndpointBuilderHelper.getRequestInitializer());

            //CheckInEndpoint checkinEndpoint =
            //        CloudEndpointBuilderHelper.updateBuilder(endpointBuilder).build().checkInEndpoint();

            return null;
        }
    }
}
