package com.xtel.vparking.vip.view.activity.inf;

import android.app.Activity;

/**
 * Created by Lê Công Long Vũ on 12/19/2016.
 */

public interface IParkingView {

    void onGetDataSuccess(int id);
    void onGetDataError();
    Activity getActivity();
}
