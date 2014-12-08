package nfiniteloop.net.loqale;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by vaek on 12/8/14.
 */
public class LoqalePageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    public LoqalePageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;

    }

    public Fragment getItem(int i) {
        return (Fragment) this.fragments.get(i);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
