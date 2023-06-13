package com.example.fleetfare.Models;

import java.io.Serializable;

public class Record implements Serializable {

    public String id;
    String date;
    String vehicleNO;
    String comments="";
    float weight;
    float weightDeductionPercent=0;
    float weightAfterDeduction;
    float pricePerMann;
    float weightInMann;
    float totalPayement;


    public Record() {
    }

    public Record(String vehicleNO, float totalPayement) {
        this.vehicleNO = vehicleNO;
        this.totalPayement = totalPayement;
    }

    public Record(String date, String vehicleNO, String comments, float weight,
                  float weightDeductionPercent, float weightAfterDeduction, float pricePerMann,
                  float weightInMann, float totalPayement) {
        this.date = date;
        this.vehicleNO = vehicleNO;
        this.comments = comments;
        this.weight = weight;
        this.weightDeductionPercent = weightDeductionPercent;
        this.weightAfterDeduction = weightAfterDeduction;
        this.pricePerMann = pricePerMann;
        this.weightInMann = weightInMann;
        this.totalPayement = totalPayement;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVehicleNO() {
        return vehicleNO;
    }

    public void setVehicleNO(String vehicleNO) {
        this.vehicleNO = vehicleNO;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getWeightDeductionPercent() {
        return weightDeductionPercent;
    }

    public void setWeightDeductionPercent(float weightDeductionPercent) {
        this.weightDeductionPercent = weightDeductionPercent;
    }

    public float getWeightAfterDeduction() {
        return weightAfterDeduction;
    }

    public void setWeightAfterDeduction(float weightAfterDeduction) {
        this.weightAfterDeduction = weightAfterDeduction;
    }

    public float getPricePerMann() {
        return pricePerMann;
    }

    public void setPricePerMann(float pricePerMann) {
        this.pricePerMann = pricePerMann;
    }

    public float getWeightInMann() {
        return weightInMann;
    }

    public void setWeightInMann(float weightInMann) {
        this.weightInMann = weightInMann;
    }

    public float getTotalPayement() {
        return totalPayement;
    }
    public String getTotalPayementInString() {
        return Float.toString(totalPayement);
    }

    public void setTotalPayement(float totalPayement) {
        this.totalPayement = totalPayement;
    }

}
