package com.example.fleetfare.Models;

import java.io.Serializable;

public class Stats implements Serializable {
    int numRecords=0;
    float totalAmount= 0.0F, totalWeight= 0.0F;
    public void addnumRecords(int i){
        numRecords+=i;
    }
    public void subtractnumRecords(int i){
        numRecords-=i;
    }

    public void addtotalAmount(float i){
        totalAmount+=i;
    }
    public void subtracttotalAmount(float i){
        totalAmount-=i;
    }

    public void addtotalWeight(float i){
        totalWeight+=i;
    }
    public void subtracttotalWeight(float i){
        totalWeight-=i;
    }
    public int getNumRecords() {
        return numRecords;
    }

    public void setNumRecords(int numRecords) {
        this.numRecords = numRecords;
    }


    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(float totalWeight) {
        this.totalWeight = totalWeight;
    }



    public Stats() {
    }

    public Stats(int numRecords, float totalAmount, float totalWeight) {
        this.numRecords = numRecords;
        this.totalAmount = totalAmount;
        this.totalWeight = totalWeight;
    }
}
