package com.xtel.vparking.vip.view.activity.inf;

import android.app.Activity;

import com.xtel.vparking.vip.model.entity.Error;
import com.xtel.vparking.vip.model.entity.ParkingCheckIn;

import java.util.ArrayList;

/**
 * Created by Mr. M.2 on 12/2/2016.
 */

public interface IViewCheckIn extends BasicView {

    void showShortToast(String message);

    void onNetworkDisable();

    void onGetVerhicleSuccess(ArrayList<ParkingCheckIn> arrayList);

    void onGetVerhicleError(Error error);

    void onItemClicked(ParkingCheckIn checkIn);

    Activity getActivity();
}
