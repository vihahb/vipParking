package com.xtel.vparking.vip.model.entity;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by Lê Công Long Vũ on 12/14/2016.
 */

public class RESP_Check_In extends RESP_Basic {
    @Expose
    private ArrayList<CheckIn> data;

    public ArrayList<CheckIn> getData() {
        return data;
    }

    public void setData(ArrayList<CheckIn> data) {
        this.data = data;
    }
}