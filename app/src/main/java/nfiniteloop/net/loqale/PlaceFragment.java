package nfiniteloop.net.loqale;

import android.app.ListFragment;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * no listener, since were just displaying static items
 */
public class PlaceFragment extends ListFragment {

    MessageAdapter adapter;
    private List<PlaceItem> items;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlaceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        items = new ArrayList<PlaceItem>();

        // TODO: Change Adapter to display your content
        setListAdapter(new PlaceAdapter(getActivity(), items));
    }

    public void populateList() {

    }
}