package com.xtel.vparking.vip.presenter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.xtel.vparking.vip.R;
import com.xtel.vparking.vip.callback.RequestNoResultListener;
import com.xtel.vparking.vip.callback.ResponseHandle;
import com.xtel.vparking.vip.commons.Constants;
import com.xtel.vparking.vip.commons.GetNewSession;
import com.xtel.vparking.vip.model.LoginModel;
import com.xtel.vparking.vip.model.ParkingModel;
import com.xtel.vparking.vip.model.entity.Error;
import com.xtel.vparking.vip.model.entity.Find;
import com.xtel.vparking.vip.model.entity.RESP_Parking;
import com.xtel.vparking.vip.model.entity.RESP_Parking_Info;
import com.xtel.vparking.vip.model.entity.RESP_Router;
import com.xtel.vparking.vip.model.entity.Steps;
import com.xtel.vparking.vip.utils.PermissionHelper;
import com.xtel.vparking.vip.view.activity.HomeActivity;
import com.xtel.vparking.vip.view.activity.inf.HomeFragmentView;
import com.xtel.vparking.vip.view.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lê Công Long Vũ on 12/4/2016.
 */

public class HomeFragmentPresenter {
    private HomeFragmentView view;
    private boolean isViewing = true;
    private String[] permission;

    public HomeFragmentPresenter(HomeFragmentView view) {
        this.view = view;
        initPermission();
    }

    private void initPermission() {
        if (permission == null)
            permission = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    }

    public void checkResultSearch() {
        if (HomeActivity.PARKING_ID != -1) {
            if (isViewing) {
                view.onSearchParking(HomeActivity.PARKING_ID);
                getParkingInfo(HomeActivity.PARKING_ID);
            }
        }

        HomeActivity.PARKING_ID = -1;
    }

    public void checkFindOption(Find find_option) {
        if (find_option.getType() == -1 && find_option.getPrice_type() == -1 && find_option.getPrice() == -1 &&
                find_option.getBegin_time().isEmpty() && find_option.getEnd_time().isEmpty()) {
            if (isViewing)
                view.onCheckFindOptionSuccess(R.mipmap.ic_filter);
        } else if (isViewing)
            view.onCheckFindOptionSuccess(R.mipmap.ic_edit_filter);
    }

    public void getMyLocation() {
        if (HomeFragment.mGoogleApiClient.isConnected()) {
            if (permission == null || permission.length == 0 || view.getActivity() == null)
                return;

            if (PermissionHelper.checkListPermission(permission, view.getActivity(), 1001)) {
                Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(HomeFragment.mGoogleApiClient);
                if (mLastLocation != null) {
                    if (isViewing)
                        view.onGetMyLocationSuccess(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
                } else
                    view.onGetMyLocationError(view.getActivity().getString(R.string.can_not_find_location));
            }
        } else
            HomeFragment.mGoogleApiClient.connect();
    }

    public void getParkingInfo(final int id) {
        String url = Constants.SERVER_PARKING + Constants.PARKING_INFO + id;
        String session = LoginModel.getInstance().getSession();

        ParkingModel.getInstanse().getParkingInfo(url, session, new ResponseHandle<RESP_Parking_Info>(RESP_Parking_Info.class) {
            @Override
            public void onSuccess(RESP_Parking_Info obj) {
                if (isViewing)
                    view.onGetParkingInfoSuccess(obj);
            }

            @Override
            public void onError(Error error) {
                if (error.getCode() == 2)
                    getNewSessionParkingInfo(id);
                else if (isViewing)
                    view.onGetParkingInfoError(error);
            }

            @Override
            public void onUpdate() {
                view.onUpdateVersion();
            }
        });
    }

    private void getNewSessionParkingInfo(final int id) {
        GetNewSession.getNewSession(view.getActivity(), new RequestNoResultListener() {
            @Override
            public void onSuccess() {
                getParkingInfo(id);
            }

            @Override
            public void onError() {
                if (isViewing)
                    view.onGetParkingInfoError(new Error(2, "ERROR", view.getActivity().getString(R.string.error_session_invalid)));
            }
        });
    }

    public void getParkingAround(final double lat, final double lng, final Find find) {
        String url = Constants.SERVER_PARKING + Constants.PARKING_FIND + Constants.PARKING_LAT + lat + Constants.PARKING_LNG + lng;
        if (find.getPrice() != -1)
            url += Constants.PARKING_PRICE + find.getPrice();
        if (find.getPrice_type() != -1)
            url += Constants.PARKING_PRICE_TYPE + find.getPrice_type();
        if (find.getType() != -1)
            url += Constants.PARKING_TYPE + find.getType();
        if (!find.getBegin_time().isEmpty())
            url += Constants.PARKING_BEGIN_TIME + find.getBegin_time();
        if (!find.getEnd_time().isEmpty())
            url += Constants.PARKING_END_TIME + find.getEnd_time();

        ParkingModel.getInstanse().getParkingAround(url, new ResponseHandle<RESP_Parking>(RESP_Parking.class) {
            @Override
            public void onSuccess(RESP_Parking obj) {
                if (isViewing)
                    view.onGetParkingAroundSuccess(obj.getData());
            }

            @Override
            public void onError(Error error) {
                if (isViewing)
                    if (error.getCode() == 2)
                        getNewSessionParkingAroung(lat, lng, find);
                    else
                        view.onGetParkingAroundError(error);
            }

            @Override
            public void onUpdate() {
                view.onUpdateVersion();
            }
        });
    }

    private void getNewSessionParkingAroung(final double lat, final double lng, final Find find) {
        GetNewSession.getNewSession(view.getActivity(), new RequestNoResultListener() {
            @Override
            public void onSuccess() {
                getParkingAround(lat, lng, find);
            }

            @Override
            public void onError() {

            }
        });
    }

    public void getPolyLine(final double from_lat, final double from_lng, double to_lat, double to_lng) {
        String url = Constants.POLY_HTTP + from_lat + "," + from_lng + Constants.POLY_DESTINATION + to_lat + "," + to_lng;
        ParkingModel.getInstanse().getPolyLine(url, new ResponseHandle<RESP_Router>(RESP_Router.class) {
            @Override
            public void onSuccess(RESP_Router obj) {
                if (obj != null) {
                    LatLng latLng = new LatLng(from_lat, from_lng);
                    PolylineOptions polylineOptions = getPolylineOption(obj.getRoutes().get(0).getLegs().get(0).getSteps());

                    if (isViewing)
                        if (polylineOptions != null)
                            view.onGetPolylineSuccess(latLng, polylineOptions);
                        else
                            view.onGetPolylineError("Không thể đẫn đường");
                }
            }

            @Override
            public void onError(Error error) {
                if (isViewing)
                    view.onGetPolylineError("Không thể đẫn đường");
            }

            @Override
            public void onUpdate() {
                view.onUpdateVersion();
            }
        });
    }

    private PolylineOptions getPolylineOption(ArrayList<Steps> steps) {
        try {
            PolylineOptions polylineOptions = new PolylineOptions();

            for (int i = 0; i < steps.size(); i++) {
                List<LatLng> poly = Constants.decodePoly(steps.get(i).getPolyline().getPoints());

                for (int j = 0; j < poly.size(); j++) {
                    polylineOptions.add(poly.get(j));
                }
            }

            return polylineOptions;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void destroyView() {
        isViewing = false;
    }
}