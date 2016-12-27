package com.xtel.vparking.vip.view.activity.inf;

import android.app.Activity;

import com.xtel.vparking.vip.model.entity.Brandname;

import java.util.ArrayList;

/**
 * Created by vivhp on 12/9/2016.
 */

public interface AddVerhicleView extends IView {

    void onAddVerhicleSuccess();

    void onAddVerhicleError();

    void finishActivity();

    void putExtra(String key, int id);

    @Override
    void showShortToast(String message);

    void onGetBrandnameData(ArrayList<Brandname> brandNames);

    Activity getActivity();

}
