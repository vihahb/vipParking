package com.xtel.vparking.vip.model.entity;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by Lê Công Long Vũ on 11/5/2016.
 */

public class RESP_Parking extends RESP_Basic {
    @Expose
    private ArrayList<Parking> data;

    public ArrayList<Parking> getData() {
        return data;
    }

    public void setData(ArrayList<Parking> data) {
        this.data = data;
    }


}
