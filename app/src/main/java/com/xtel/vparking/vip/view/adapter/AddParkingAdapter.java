package com.xtel.vparking.vip.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xtel.vparking.vip.model.entity.Pictures;
import com.xtel.vparking.vip.view.fragment.ViewImageFragment;

import java.util.ArrayList;

/**
 * Created by Lê Công Long Vũ on 12/9/2016
 */

public class AddParkingAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Pictures> arrayList;

    public AddParkingAdapter(FragmentManager fm, ArrayList<Pictures> arrayList) {
        super(fm);
        this.arrayList = arrayList;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return ViewImageFragment.newInstance(arrayList.get(position).getUrl());
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }
}
