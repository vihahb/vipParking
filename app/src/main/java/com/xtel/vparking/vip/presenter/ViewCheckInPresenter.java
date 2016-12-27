package com.xtel.vparking.vip.presenter;

import android.os.Handler;

import com.xtel.vparking.vip.callback.RequestNoResultListener;
import com.xtel.vparking.vip.callback.ResponseHandle;
import com.xtel.vparking.vip.commons.GetNewSession;
import com.xtel.vparking.vip.commons.NetWorkInfo;
import com.xtel.vparking.vip.model.VerhicleModel;
import com.xtel.vparking.vip.model.entity.Error;
import com.xtel.vparking.vip.model.entity.RESP_Parking_Check_In;
import com.xtel.vparking.vip.view.activity.inf.IViewCheckIn;

/**
 * Created by Lê Công Long Vũ on 12/2/2016.
 */

public class ViewCheckInPresenter {
    private IViewCheckIn view;
    private boolean isViewing = true;
    private int id = -1, page = 1, size = 10;

    public ViewCheckInPresenter(IViewCheckIn view, int id) {
        this.view = view;
        this.id = id;
    }

    public void getCheckIn() {
        if (!NetWorkInfo.isOnline(view.getActivity())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.onNetworkDisable();
                }
            }, 500);

            return;
        }

        VerhicleModel.getInstance().getCheckInByParkingId(id, page, size, new ResponseHandle<RESP_Parking_Check_In>(RESP_Parking_Check_In.class) {
            @Override
            public void onSuccess(RESP_Parking_Check_In obj) {
                if (isViewing) {
                    page++;
                    size += 8;
                    view.onGetVerhicleSuccess(obj.getData());
                }
            }

            @Override
            public void onError(Error error) {
                if (error.getCode() == 2)
                    getNewSessionVerhicle();
                else if (isViewing)
                    view.onGetVerhicleError(error);
            }
        });
    }

    private void getNewSessionVerhicle() {
        GetNewSession.getNewSession(view.getActivity(), new RequestNoResultListener() {
            @Override
            public void onSuccess() {
                if (isViewing)
                    getCheckIn();
            }

            @Override
            public void onError() {
                if (isViewing)
                    view.onGetVerhicleError(new Error(2, "ERROR", "Bạn đã hết phiên làm việc"));
            }
        });
    }

    public void destroyView() {
        isViewing = false;
    }
}