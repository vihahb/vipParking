package com.xtel.vparking.vip.presenter;

import android.os.Handler;

import com.xtel.vparking.vip.callback.RequestNoResultListener;
import com.xtel.vparking.vip.callback.ResponseHandle;
import com.xtel.vparking.vip.commons.GetNewSession;
import com.xtel.vparking.vip.commons.NetWorkInfo;
import com.xtel.vparking.vip.model.VerhicleModel;
import com.xtel.vparking.vip.model.entity.Error;
import com.xtel.vparking.vip.model.entity.RESP_Parking_History;
import com.xtel.vparking.vip.view.activity.inf.IViewHistory;

/**
 * Created by vivhp on 12/19/2016.
 */

public class HistoryPresenter {
    private IViewHistory viewHistory;
    private boolean isViewing = true;
    int id = -1;
    int page = 1;
    int pagesize = 10;

    public HistoryPresenter(IViewHistory viewHistory, int id) {
        this.viewHistory = viewHistory;
        this.id = id;
    }

    public void setPageDefault() {
        page = 1;
    }

    public void getAllData(final String time) {
        if (!NetWorkInfo.isOnline(viewHistory.getActivity())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewHistory.onNetworkDisable();
                }
            }, 500);
            return;
        }

        VerhicleModel.getInstance().getDataHistory(id, time, time, page, pagesize, new ResponseHandle<RESP_Parking_History>(RESP_Parking_History.class) {
            @Override
            public void onSuccess(RESP_Parking_History obj) {
                if (isViewing) {
                    page++;
                    viewHistory.onGetHistorySuccess(obj.getData());
                }
            }

            @Override
            public void onError(Error error) {
                if (error.getCode() == 2) {
                    getNewSessionHistory(time);
                } else if (isViewing) {
                    viewHistory.onGetHistoryError(error);
                }
            }

            @Override
            public void onUpdate() {
                viewHistory.onUpdateVersion();
            }
        });
    }

    private void getNewSessionHistory(final String begin) {
        GetNewSession.getNewSession(viewHistory.getActivity(), new RequestNoResultListener() {
            @Override
            public void onSuccess() {
                getAllData(begin);
            }

            @Override
            public void onError() {
                if (isViewing)
                    viewHistory.onGetHistoryError(new Error(2, "ERROR", "Bạn đã hết phiên làm việc."));
            }
        });
    }

    public void destroyViewing() {
        isViewing = false;
    }
}
