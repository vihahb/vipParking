package com.xtel.vparking.vip.view.activity.inf;

import android.app.Activity;

import com.xtel.vparking.vip.model.entity.Error;
import com.xtel.vparking.vip.model.entity.ParkingInfo;

import java.util.ArrayList;

/**
 * Created by Mr. M.2 on 12/5/2016.
 */

public interface ManagementView {

    void onNetworkDisable();
    void onGetParkingByUserSuccess(ArrayList<ParkingInfo> arrayList);
    void onGetParkingByUserError(Error error);
    void onGetParkingInfoSuccess(ParkingInfo parkingInfo);
    Activity getActivity();
}