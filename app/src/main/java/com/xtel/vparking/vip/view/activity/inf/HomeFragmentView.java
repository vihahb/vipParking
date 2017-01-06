package com.xtel.vparking.vip.view.activity.inf;

import android.app.Activity;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.xtel.vparking.vip.model.entity.Error;
import com.xtel.vparking.vip.model.entity.Parking;
import com.xtel.vparking.vip.model.entity.RESP_Parking_Info;

import java.util.ArrayList;

/**
 * Created by Mr. M.2 on 12/2/2016.
 */

public interface HomeFragmentView extends IView {

    void onGetMyLocationSuccess(LatLng latLng);
    void onGetMyLocationError(String error);
    void onGetParkingInfoSuccess(RESP_Parking_Info resp_parking_info);
    void onGetParkingInfoError(Error error);
    void onSearchParking(int id);
    void onGetParkingAroundSuccess(ArrayList<Parking> arrayList);
    void onGetParkingAroundError(Error error);
    void onGetPolylineSuccess(LatLng latLng, PolylineOptions polylineOptions);
    void onGetPolylineError(String error);
    void onCheckFindOptionSuccess(int image);
    Activity getActivity();
}
