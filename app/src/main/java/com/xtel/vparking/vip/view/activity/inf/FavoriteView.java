package com.xtel.vparking.vip.view.activity.inf;

import android.app.Activity;

import com.xtel.vparking.vip.model.entity.Error;
import com.xtel.vparking.vip.model.entity.Favotire;

import java.util.ArrayList;

/**
 * Created by Lê Công Long Vũ on 12/5/2016.
 */

public interface FavoriteView {

    void onNetworkDisable();
    void onRemoveItemSuccess();
    void showShortToast(String message);
    void showLongToast(String message);
    void onGetParkingFavoriteSuccess(ArrayList<Favotire> arrayList);
    void onGetParkingFavoriteError(Error error);
    Activity getActivity();
}
