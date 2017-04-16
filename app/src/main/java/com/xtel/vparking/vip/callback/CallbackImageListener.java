package com.xtel.vparking.vip.callback;

import com.xtel.vparking.vip.model.entity.RESP_Image;

import java.io.File;

/**
 * Created by Lê Công Long Vũ on 12/1/2016
 */

public interface CallbackImageListener {
    void onSuccess(RESP_Image resp_image, File file);
    void onError();
}