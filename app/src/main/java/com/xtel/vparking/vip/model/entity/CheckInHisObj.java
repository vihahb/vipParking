package com.xtel.vparking.vip.model.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by vivhp on 12/19/2016.
 */

public class CheckInHisObj extends RESP_Basic implements Serializable {

    @Expose
    private String transaction;
    @Expose
    private String checkin_time;
    @Expose
    private String checkout_time;
    @Expose
    private int checkin_type;
    @Expose
    private String ticket_code;
    @Expose
    private UserModel user;
    @Expose
    private Verhicle vehicle;

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getCheckin_time() {
        return checkin_time;
    }

    public void setCheckin_time(String checkin_time) {
        this.checkin_time = checkin_time;
    }

    public String getCheckout_time() {
        return checkout_time;
    }

    public void setCheckout_time(String checkout_time) {
        this.checkout_time = checkout_time;
    }

    public int getCheckin_type() {
        return checkin_type;
    }

    public void setCheckin_type(int checkin_type) {
        this.checkin_type = checkin_type;
    }

    public String getTicket_code() {
        return ticket_code;
    }

    public void setTicket_code(String ticket_code) {
        this.ticket_code = ticket_code;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public Verhicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Verhicle vehicle) {
        this.vehicle = vehicle;
    }
}
