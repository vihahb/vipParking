package com.xtel.vparking.vip.model.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Lê Công Long Vũ on 12/14/2016.
 */

public class CheckIn implements Serializable {
    @Expose
    private String transaction;
    @Expose
    private String checkin_time;
    @Expose
    private int checkin_type;
    @Expose
    private String ticket_code;
    @Expose
    private ParkingObj parking;
    @Expose
    private VerhicleObj vehicle;

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

    public ParkingObj getParking() {
        return parking;
    }

    public void setParking(ParkingObj parking) {
        this.parking = parking;
    }

    public VerhicleObj getVehicle() {
        return vehicle;
    }

    public void setVehicle(VerhicleObj vehicle) {
        this.vehicle = vehicle;
    }
}