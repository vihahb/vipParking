package com.xtel.vparking.vip.model;

import com.xtel.vparking.vip.callback.RequestServer;
import com.xtel.vparking.vip.commons.Constants;

/**
 * Created by Mr. M.2 on 12/2/2016.
 */

public abstract class BasicModel {
    protected RequestServer requestServer = new RequestServer();

    protected String getNIPUMApiUrlBase() {
        return Constants.SERVER_AUTHEN;
    }

    protected String getParkingApiUrlBase() {
        return Constants.SERVER_PARKING;
    }
}
