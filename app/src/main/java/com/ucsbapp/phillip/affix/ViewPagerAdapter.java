package com.ucsbapp.phillip.affix;

/**
 * Created by Phillip on 8/14/2016.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new TabFragment();
        }
        else{

        }
        // tells which Fragment to display with the viewpager for tab position
        return new TabFragment();
    }

    @Override
    public int getCount() {
        //Tells the number of tabs to make
        return 3;
    }
}

