package com.xtel.vparking.vip.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xtel.vparking.vip.view.fragment.ViewImageFragment;

import java.util.ArrayList;

/**
 * Created by Computer on 11/10/2016.
 */

public class ViewImageAdapter extends FragmentStatePagerAdapter {
    private ArrayList<String> fragments;

    public ViewImageAdapter(FragmentManager fm, ArrayList<String> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return ViewImageFragment.newInstance(fragments.get(position));
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
