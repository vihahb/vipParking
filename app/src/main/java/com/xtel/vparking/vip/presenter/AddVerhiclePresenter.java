package com.xtel.vparking.vip.presenter;

import android.content.Context;
import android.util.Log;

import com.xtel.vparking.vip.R;
import com.xtel.vparking.vip.callback.RequestNoResultListener;
import com.xtel.vparking.vip.callback.ResponseHandle;
import com.xtel.vparking.vip.commons.Constants;
import com.xtel.vparking.vip.commons.GetNewSession;
import com.xtel.vparking.vip.model.LoginModel;
import com.xtel.vparking.vip.model.VerhicleModel;
import com.xtel.vparking.vip.model.entity.Brandname;
import com.xtel.vparking.vip.model.entity.Error;
import com.xtel.vparking.vip.model.entity.RESP_Brandname;
import com.xtel.vparking.vip.model.entity.RESP_Parking_Info;
import com.xtel.vparking.vip.model.entity.RESP_Verhicle;
import com.xtel.vparking.vip.utils.JsonHelper;
import com.xtel.vparking.vip.view.activity.inf.AddVerhicleView;

import java.util.ArrayList;

/**
 * Created by vivhp on 12/9/2016.
 */

public class AddVerhiclePresenter {
    public AddVerhicleView view;

    public AddVerhiclePresenter(AddVerhicleView view) {
        this.view = view;
    }

    public void getVerhicleBrandname() {
        int version = 0;
        String url_brand_name = Constants.SERVER_PARKING
                + Constants.GET_BRANDNAME
                + Constants.BRAND_NAME
                + Constants.BRANDNAME_VERSION
                + version;

        VerhicleModel.getInstance().getBrandName(url_brand_name, new ResponseHandle<RESP_Brandname>(RESP_Brandname.class) {
            @Override
            public void onSuccess(RESP_Brandname obj) {
                Log.v("brandname", obj.toString());
                view.onGetBrandnameData(obj.getData());
            }

            @Override
            public void onError(Error error) {
                Log.e("brandname err code", "null k: " + String.valueOf(error.getCode()));
                Log.e("brandname err mess", "null k: " + error.getMessage());
            }
        });

    }

    public void addVerhicle(final String name, final String plate, final String des, final int type, final int flag, final String brand_code) {

        String url = Constants.SERVER_PARKING + Constants.ADD_VERHICLE;
        String session = LoginModel.getInstance().getSession();
        RESP_Verhicle resp_verhicle = new RESP_Verhicle();
        resp_verhicle.setName(name);
        resp_verhicle.setPlate_number(plate);
        resp_verhicle.setDesc(des);
        resp_verhicle.setType(type);
        resp_verhicle.setFlag_default(flag);
        resp_verhicle.setBrandname(setCode(brand_code));

        VerhicleModel.getInstance().addVerhicle2Nip(url, JsonHelper.toJson(resp_verhicle), session, new ResponseHandle<RESP_Verhicle>(RESP_Verhicle.class) {
            @Override
            public void onSuccess(RESP_Verhicle obj) {
                Log.v("Verhicle ", "i1 " + obj.getId());
                view.showProgressBar(false, false, null, view.getActivity().getString(R.string.parking_get_data));
                putId(obj.getId());
                view.showShortToast(view.getActivity().getString(R.string.verhicle_add_success));
            }

            @Override
            public void onError(Error error) {
                if (error.getCode() == 2) {
                    getNewSessionAddVerhicle(name, plate, des, type, flag, brand_code);
                } else
//                    view.showShortToast(error.getMessage());
                Log.e("Err add v", String.valueOf(error.getCode()));
                Log.e("Err add v type", error.getMessage());
            }
        });
    }


    public void updateVerhicle(final int id, final String name, final String plate, final String des, final int type, final int flag, final String brand_code) {

        String url = Constants.SERVER_PARKING + Constants.ADD_VERHICLE;
        String session = LoginModel.getInstance().getSession();
        final RESP_Verhicle resp_verhicle = new RESP_Verhicle();
        resp_verhicle.setId(id);
        resp_verhicle.setName(name);
        resp_verhicle.setPlate_number(plate);
        resp_verhicle.setDesc(des);
        resp_verhicle.setType(type);
        resp_verhicle.setFlag_default(flag);
        resp_verhicle.setBrandname(setCode(brand_code));
        final int id_put = resp_verhicle.getId();

        Log.v("Verhicle", JsonHelper.toJson(resp_verhicle));

        VerhicleModel.getInstance().putVerhicle2Server(url, JsonHelper.toJson(resp_verhicle), session, new ResponseHandle<RESP_Parking_Info>(RESP_Parking_Info.class) {
            @Override
            public void onSuccess(RESP_Parking_Info obj) {
                view.showProgressBar(false, false, null, view.getActivity().getString(R.string.parking_get_data));
                putId(id_put);
                view.showShortToast(view.getActivity().getString(R.string.update_message_success));
            }

            @Override
            public void onError(Error error) {
                if (error.getCode() == 2) {
                    getNewSessionUpdateVerhicle(id, name, plate, des, type, flag, brand_code);
                } else
                    view.showShortToast(error.getMessage());
                Log.e("Err add v2", String.valueOf(error.getCode()));
                Log.e("Err add v2 type", error.getMessage());
            }
        });
    }

    private void getNewSessionAddVerhicle(final String name, final String plate, final String des, final int type, final int flag, final String brand_code) {
        GetNewSession.getNewSession(view.getActivity(), new RequestNoResultListener() {
            @Override
            public void onSuccess() {
                addVerhicle(name, plate, des, type, flag, brand_code);
            }

            @Override
            public void onError() {
                view.showShortToast(view.getActivity().getString(R.string.error_session_invalid));
            }
        });
    }

    private void getNewSessionUpdateVerhicle(final int id, final String name, final String plate, final String des, final int type, final int flag, final String brand_code) {
        GetNewSession.getNewSession(view.getActivity(), new RequestNoResultListener() {
            @Override
            public void onSuccess() {
                updateVerhicle(id, name, plate, des, type, flag, brand_code);
            }

            @Override
            public void onError() {
                view.showShortToast(view.getActivity().getString(R.string.error_session_invalid));
            }
        });
    }

    public Brandname setCode(String code) {
        Brandname brandname = new Brandname();
        brandname.setCode(code);
        return brandname;
    }

    private void putId(int id) {
        view.putExtra(Constants.VERHICLE_ID, id);
    }

}
