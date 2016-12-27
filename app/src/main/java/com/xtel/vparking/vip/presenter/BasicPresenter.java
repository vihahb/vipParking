package com.xtel.vparking.vip.presenter;

import android.app.Activity;

import com.xtel.vparking.vip.callback.ICmd;
import com.xtel.vparking.vip.callback.RequestNoResultListener;
import com.xtel.vparking.vip.commons.GetNewSession;

/**
 * Created by Mr. M.2 on 12/19/2016.
 */

public abstract class BasicPresenter {

    protected void getNewSession(Activity activity, final ICmd cmd) {
        GetNewSession.getNewSession(activity, new RequestNoResultListener() {
            @Override
            public void onSuccess() {
               cmd.execute();
            }

            @Override
            public void onError() {
                getSessionError();
            }
        });
    }

    public abstract void getSessionError();
}
