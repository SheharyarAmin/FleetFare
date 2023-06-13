package com.example.fleetfare.Models;

import java.io.Serializable;

public class DayItem implements Serializable {
    String vehicleNumber, Amount;

    public DayItem() {
    }

    public DayItem(String vehicleNumber, String amount) {
        this.vehicleNumber = vehicleNumber;
        Amount = amount;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
