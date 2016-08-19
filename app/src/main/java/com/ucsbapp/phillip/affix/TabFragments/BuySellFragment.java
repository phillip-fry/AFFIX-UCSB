package com.ucsbapp.phillip.affix.TabFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ucsbapp.phillip.affix.R;

/**
 * Created by Phillip on 8/14/2016.
 */
public class BuySellFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.buysellfragment, container, false);
    }

}
