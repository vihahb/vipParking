package com.xtel.vparking.vip.model.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Lê Công Long Vũ on 12/5/2016.
 */

public class Parking implements Serializable {
    @Expose
    private int id;
    @Expose
    private int uid;
    @Expose
    private double lat;
    @Expose
    private double lng;
    @Expose
    private double type;
    @Expose
    private double status;
    @Expose
    private String code;
    @Expose
    private String begin_time;
    @Expose
    private String end_time;
    @Expose
    private String address;
    @Expose
    private int owner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getType() {
        return type;
    }

    public void setType(double type) {
        this.type = type;
    }

    public double getStatus() {
        return status;
    }

    public void setStatus(double status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
}
