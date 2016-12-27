package com.xtel.vparking.vip.view.activity.inf;

import android.app.Activity;

import com.xtel.vparking.vip.model.entity.CheckIn;
import com.xtel.vparking.vip.model.entity.Error;

import java.util.ArrayList;

/**
 * Created by Mr. M.2 on 12/2/2016.
 */

public interface CheckedView {

    void showShortToast(String message);

    void onNetworkDisable();

    void onGetVerhicleSuccess(ArrayList<CheckIn> arrayList);

    void onGetVerhicleError(Error error);

    void onItemClicked(CheckIn checkIn);

    Activity getActivity();
}
