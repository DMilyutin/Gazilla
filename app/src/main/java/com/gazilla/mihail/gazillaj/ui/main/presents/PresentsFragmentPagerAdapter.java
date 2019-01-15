package com.gazilla.mihail.gazillaj.ui.main.presents;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.widget.Switch;

import com.gazilla.mihail.gazillaj.ui.main.presents.tabPresent.GiftsFragment;
import com.gazilla.mihail.gazillaj.ui.main.presents.tabPresent.PresentsFragment;

public class PresentsFragmentPagerAdapter extends FragmentPagerAdapter {

    private GiftsFragment giftsFragment;
    private PresentsFragment presentsFragment;
    private FragmentTransaction fragmentTransaction;

    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "За баллы", "Подарки" };
    private Context context;

    public PresentsFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        giftsFragment = new GiftsFragment();
        presentsFragment = new PresentsFragment();

        //fragmentTransaction =
    }


    @Override
    public int getCount() {
        return tabTitles.length;
    }


    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0: {
                return PresentsFragment.newInstance(i+1);
            }
            case 1:{

                return GiftsFragment.newInstance(i+1);
            }
        }
        return null;

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
