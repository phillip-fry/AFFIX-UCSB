package com.ucsbapp.phillip.affix;

/**
 * Created by Phillip on 8/14/2016.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ucsbapp.phillip.affix.TabFragments.BuySellFragment;
import com.ucsbapp.phillip.affix.TabFragments.VTourFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BuySellFragment();
            case 1:
                return new VTourFragment();
            case 3:
                return new BuySellFragment();
        }
        return null;
    }
        /*
        if(position == 0){
            return new BuySellFragment();
        }
        else{

        }
        // tells which Fragment to display with the viewpager for tab position
        return new BuySellFragment();
        */


    @Override
    public int getCount() {
        //Tells the number of tab to make
        return 3;
    }
}

