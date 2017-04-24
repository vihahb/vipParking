package com.xtel.vparking.vip.view.activity.inf;

import android.app.Activity;

import com.xtel.vparking.vip.model.entity.Error;
import com.xtel.vparking.vip.model.entity.Verhicle;

import java.util.ArrayList;

/**
 * Created by Lê Công Long Vũ on 12/10/2016.
 */

public interface VerhicleView extends BasicView {

    void showShortToast(String message);
    void onNetworkDisable();
    void onGetVerhicleSuccess(ArrayList<Verhicle> arrayList);
    void onGetVerhicleError(Error error);
    void onGetVerhicleByIdSuccess(Verhicle verhicle);
    void onItemClicked(int position, Verhicle verhicle);
    Activity getActivity();
}
