package nfiniteloop.net.loqale;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;

import java.util.List;

/**
 * Created by vaek on 12/8/14.
 */
public class LoqalePageAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<Fragment> fragments;
    public LoqalePageAdapter(Context context,FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
        this.context=context;

    }

    public Fragment getItem(int i) {
        Bundle bundle = new Bundle();
        return Fragment.instantiate(this.context, this.fragments.get(i).getClass().getName(), bundle);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
