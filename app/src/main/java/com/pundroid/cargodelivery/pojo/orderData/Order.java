package com.pundroid.cargodelivery.pojo.orderData;

import com.pundroid.cargodelivery.pojo.orderData.DepartureAddress;
import com.pundroid.cargodelivery.pojo.orderData.DestinationAddress;

import java.io.Serializable;

/**
 * Created by pumba30 on 28.09.2015.
 */
public class Order implements Serializable {


    private String uuid;
    private String number;
    private DepartureAddress departureAddress;
    private DestinationAddress destinationAddress;
    private String good;
    private Long actualWeight;
    private Long appointmentFrom;
    private String note1;
    private Long initialPrice;
    private String status;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public DepartureAddress getDepartureAddress() {
        return departureAddress;
    }

    public void setDepartureAddress(DepartureAddress departureAddress) {
        this.departureAddress = departureAddress;
    }

    public DestinationAddress getDestinationAddress() {
        return destinationAddress;
    }


    public void setDestinationAddress(DestinationAddress destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getGood() {
        return good;
    }


    public void setGood(String good) {
        this.good = good;
    }


    public Long getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(Long actualWeight) {
        this.actualWeight = actualWeight;
    }

    public Long getAppointmentFrom() {
        return appointmentFrom;
    }

    public void setAppointmentFrom(Long appointmentFrom) {
        this.appointmentFrom = appointmentFrom;
    }

    public String getNote1() {
        return note1;
    }


    public void setNote1(String note1) {
        this.note1 = note1;
    }

    public Long getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(Long initialPrice) {
        this.initialPrice = initialPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
