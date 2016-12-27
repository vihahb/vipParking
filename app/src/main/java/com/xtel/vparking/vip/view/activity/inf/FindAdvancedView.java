package com.xtel.vparking.vip.view.activity.inf;

import android.app.Activity;
import android.view.View;

import com.xtel.vparking.vip.model.entity.Find;

/**
 * Created by vivhp on 12/9/2016.
 */

public interface FindAdvancedView extends IView {

    void onFindSuccess();
    void onFindError();
    void putExtras(String key, Find find);
    void showSnackbar(View view, String message);
    Activity getActivity();

}
