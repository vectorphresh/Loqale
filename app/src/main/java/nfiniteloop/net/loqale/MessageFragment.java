package nfiniteloop.net.loqale;

import android.content.Context;
import android.content.SharedPreferences;
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

import net.nfiniteloop.loqale.backend.registration.Registration;
import net.nfiniteloop.loqale.backend.registration.model.RegistrationRecord;
import net.nfiniteloop.loqale.backend.registration.model.RegistrationRecordCollection;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by vaek on 12/1/14.
 */
public class MessageFragment extends ListFragment {
    private Logger log = Logger.getLogger(MessageFragment.class.getName());
    private MessageAdapter mMessageFragmentAdapter;
    private Registration mRegistrationService;
    private WeakReference<MessageGetterTask> mAsyncTaskWeakRef;


    public static MessageFragment newInstance(ArrayList<MessageItem> items) {
        MessageFragment mf = new MessageFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("items", items);
        mf.setArguments(bundle);

        return mf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //items.add((MessageItem) savedInstanceState.getParcelableArrayList("messages").get(0));
        setRetainInstance(true);
        MessageGetterTask mg = new MessageGetterTask(this);
        this.mAsyncTaskWeakRef = new WeakReference<MessageGetterTask>(mg);
        mg.execute();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loqale_messages,container,false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        MessageGetterTask mg = new MessageGetterTask(this);
        this.mAsyncTaskWeakRef = new WeakReference<MessageGetterTask>(mg);
        mg.execute();

    }

    public class MessageGetterTask extends AsyncTask<Void, Void, ArrayList<MessageItem> > {

        List<RegistrationRecord> foo = new ArrayList<RegistrationRecord>();
        ArrayList<MessageItem> bar = new ArrayList<MessageItem>();
        private WeakReference<MessageFragment> fragmentWeakRef;

        private MessageGetterTask (MessageFragment fragment) {
            this.fragmentWeakRef = new WeakReference<MessageFragment>(fragment);
        }

        @Override
        protected ArrayList<MessageItem> doInBackground(Void... params) {
            if (mRegistrationService == null) { // Only do this once
                Registration.Builder builder =
                        new Registration.Builder(AndroidHttp.newCompatibleTransport(),
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
                mRegistrationService = builder.build();
            }
            try {
                RegistrationRecordCollection ugh = mRegistrationService.listDevices(1).execute();
                foo.addAll(ugh.getItems());

                if(!foo.isEmpty()) {
                    MessageItem mi = new MessageItem();

                    mi.setMessage(foo.get(0).getRegId());
                    SharedPreferences prefs = getActivity()
                            .getSharedPreferences(LoqaleConstants.PREFS_NAME, Context.MODE_PRIVATE);
                    String loqaleUser = prefs.getString("username", "Loqale User");
                    mi.setUsername(loqaleUser);
                    int drawableId = R.drawable.ic_person_black_36dp;
                    mi.setPicture(getResources().getDrawable(drawableId));
                    bar.add(mi);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bar;
        }

        @Override
        protected void onPostExecute(final ArrayList<MessageItem> result) {
            if (this.fragmentWeakRef.get() != null) {
                mMessageFragmentAdapter = new MessageAdapter(getActivity(), result);
                setListAdapter(mMessageFragmentAdapter);
            }

        }
    }

}
