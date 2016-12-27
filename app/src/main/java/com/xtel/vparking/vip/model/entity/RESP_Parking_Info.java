package com.xtel.vparking.vip.model.entity;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by Lê Công Long Vũ on 12/5/2016.
 */

public class RESP_Parking_Info extends RESP_Basic {
    @Expose
    private int id;
    @Expose
    private double uid;
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
    private String parking_name;
    @Expose
    private String parking_desc;
    @Expose
    private String total_place;
    @Expose
    private String parking_phone;
    @Expose
    private String empty_number;
    @Expose
    private String qr_code;
    @Expose
    private String bar_code;
    @Expose
    private ArrayList<Prices> prices;
    @Expose
    private ArrayList<Pictures> pictures;
    @Expose
    private int favorite;
    @Expose
    private UserInfo parking_owner;

    public RESP_Parking_Info() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getUid() {
        return uid;
    }

    public void setUid(double uid) {
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

    public String getParking_name() {
        return parking_name;
    }

    public void setParking_name(String parking_name) {
        this.parking_name = parking_name;
    }

    public String getParking_desc() {
        return parking_desc;
    }

    public void setParking_desc(String parking_desc) {
        this.parking_desc = parking_desc;
    }

    public String getTotal_place() {
        return total_place;
    }

    public void setTotal_place(String total_place) {
        this.total_place = total_place;
    }

    public String getEmpty_number() {
        return empty_number;
    }

    public void setEmpty_number(String empty_number) {
        this.empty_number = empty_number;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getBar_code() {
        return bar_code;
    }

    public void setBar_code(String bar_code) {
        this.bar_code = bar_code;
    }

    public ArrayList<Prices> getPrices() {
        return prices;
    }

    public void setPrices(ArrayList<Prices> prices) {
        this.prices = prices;
    }

    public ArrayList<Pictures> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<Pictures> pictures) {
        this.pictures = pictures;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public UserInfo getParking_owner() {
        return parking_owner;
    }

    public void setParking_owner(UserInfo parking_owner) {
        this.parking_owner = parking_owner;
    }

    public String getParking_phone() {
        return parking_phone;
    }

    public void setParking_phone(String parking_phone) {
        this.parking_phone = parking_phone;
    }
}
