package com.xtel.vparking.vip.presenter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.xtel.vparking.vip.R;
import com.xtel.vparking.vip.callback.RequestNoResultListener;
import com.xtel.vparking.vip.callback.ResponseHandle;
import com.xtel.vparking.vip.commons.Constants;
import com.xtel.vparking.vip.commons.GetNewSession;
import com.xtel.vparking.vip.commons.NetWorkInfo;
import com.xtel.vparking.vip.model.HomeModel;
import com.xtel.vparking.vip.model.LoginModel;
import com.xtel.vparking.vip.model.entity.Error;
import com.xtel.vparking.vip.model.entity.RESP_Parking_Info;
import com.xtel.vparking.vip.utils.JsonParse;
import com.xtel.vparking.vip.utils.PermissionHelper;
import com.xtel.vparking.vip.utils.SharedPreferencesUtils;
import com.xtel.vparking.vip.view.activity.inf.HomeView;

import java.io.UnsupportedEncodingException;

/**
 * Created by Lê Công Long Vũ on 12/2/2016.
 */

public class HomePresenter {
    private HomeView homeView;
    private final int REQUEST_PERMISSION = 1001;

    public HomePresenter(HomeView homeView) {
        this.homeView = homeView;
        checkPermission();
        checkParkingMaster();
    }

    private void checkPermission() {
        if (PermissionHelper.checkOnlyPermission(Manifest.permission.ACCESS_FINE_LOCATION, homeView.getActivity(), REQUEST_PERMISSION)) {
            NetWorkInfo.checkGPS(homeView.getActivity());
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0) {
                boolean check = true;
                for (int grantresults : grantResults) {
                    if (grantresults == PackageManager.PERMISSION_DENIED) {
                        check = false;
                        break;
                    }
                }

                if (check) {
                    homeView.onSetMapSetting();
                    NetWorkInfo.checkGPS(homeView.getActivity());
                }
            }
        }
    }

    private void checkParkingMaster() {
        if (SharedPreferencesUtils.getInstance().getIntValue(Constants.USER_FLAG) == 1)
            homeView.isParkingMaster();
    }

    public void updateUserData() {
        String avatar = LoginModel.getInstance().getAvatar();
        String full_name = LoginModel.getInstance().getName();

        homeView.onUserDataUpdate(avatar, full_name);
    }

    public void activeParkingMaster() {
        if (!NetWorkInfo.isOnline(homeView.getActivity())) {
            homeView.showShortToast(homeView.getActivity().getString(R.string.no_internet));
            return;
        }

        try {
            String url = Constants.SERVER_PARKING + Constants.PARKING_ACTIVE;
            HomeModel.getInstance().activeParkingMaster(url, new ResponseHandle<RESP_Parking_Info>(RESP_Parking_Info.class) {
                @Override
                public void onSuccess(RESP_Parking_Info obj) {
                    SharedPreferencesUtils.getInstance().putIntValue(Constants.USER_FLAG, 1);
                    homeView.onActiveMasterSuccess();
                }

                @Override
                public void onError(Error error) {
                    if (error.getCode() == 2)
                        getNewSessionActive();
                    else
                        homeView.onActiveMasterFailed(JsonParse.getCodeMessage(error.getCode(), homeView.getActivity().getString(R.string.loi_coloi)));
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void getNewSessionActive() {
        GetNewSession.getNewSession(homeView.getActivity(), new RequestNoResultListener() {
            @Override
            public void onSuccess() {
                activeParkingMaster();
            }

            @Override
            public void onError() {
                homeView.showShortToast(homeView.getActivity().getString(R.string.error_session_invalid));
//                Toast.makeText(homeView.getActivity(), homeView.getActivity().getString(R.string.error_session_invalid), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showQrCode() {
        String qrcode = LoginModel.getInstance().getUserQrCode();

        if (qrcode != null) {
            if (!qrcode.isEmpty()) {
                homeView.onShowQrCode(qrcode);
            }
        }
    }
}
