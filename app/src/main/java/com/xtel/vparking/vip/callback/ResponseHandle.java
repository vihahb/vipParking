package com.xtel.vparking.vip.callback;


import android.util.Log;

import com.xtel.vparking.vip.model.entity.Error;
import com.xtel.vparking.vip.model.entity.RESP_Basic;
import com.xtel.vparking.vip.model.entity.RESP_Parking_Info;
import com.xtel.vparking.vip.utils.JsonHelper;

import java.io.IOException;

/**
 * Created by Lê Công Long Vũ on 12/4/2016.
 */

public abstract class ResponseHandle<T extends RESP_Basic> {
    private Class<T> clazz;

    protected ResponseHandle(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void onSuccess(String result) {
        Log.e("ResponseHandle", "Result " + result);
        try {
            boolean isJson;
            isJson = !(result == null || result.isEmpty());

            Log.e("ResponseHandle", "Is Json: " + isJson);
            if (!isJson) {
                Log.e("ResponseHandle", "Success null");
                onSuccess((T) new RESP_Parking_Info());
            } else {
                T t = JsonHelper.getObjectNoException(result, clazz);
                if (t.getError() != null) {
                    onError(t.getError());
                    Log.e("ResponseHandle", "Error");
                } else {
                    onSuccess(t);
                    Log.e("ResponseHandle", "Success");
                }
            }
        } catch (Exception e) {
            Log.e("ResponseHandle", "Error parse: " + e.toString());
            onError(new Error(-1, "ERROR_PARSER_RESPONSE", e.getMessage()));
        }
    }

    public void onError(IOException error) {
        Log.e("ResponseHandle", "Error: " + error.getMessage());
        onError(new Error(-1, "ERROR_PARSER_RESPONSE", error.getMessage()));
    }

    public abstract void onSuccess(T obj);

    public abstract void onError(Error error);
}
