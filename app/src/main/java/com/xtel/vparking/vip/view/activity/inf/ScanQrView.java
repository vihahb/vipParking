package com.xtel.vparking.vip.view.activity.inf;

import android.app.Activity;

import com.xtel.vparking.vip.model.entity.Error;
import com.xtel.vparking.vip.model.entity.Verhicle;

import java.util.ArrayList;

/**
 * Created by Mr. M.2 on 12/3/2016.
 */

public interface ScanQrView {

    void onGetVerhicleSuccess(ArrayList<Verhicle> arrayList);
    void onGetVerhicleError(Error error);
    void onStartChecking();
    void onCheckingSuccess();
    void onCheckingError(Error error);
    Activity getActivity();
}
