package com.xtel.vparking.vip.model.entity;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by vivhp on 12/9/2016.
 */

public class RESP_Brandname extends RESP_Basic {
    @Expose
    private ArrayList<Brandname> data;
    @Expose
    private int version;

    public ArrayList<Brandname> getData() {
        return data;
    }

    public void setData(ArrayList<Brandname> data) {
        this.data = data;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "RESP_Brandname{" +
                "data=" + data +
                ", version=" + version +
                '}';
    }
}
