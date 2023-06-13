package com.example.fleetfare.Interfaces;

import com.example.fleetfare.Models.Record;

import java.util.List;

public interface onMonthDataFetched {
    void onSuccess(List<Record> records);
    void onFailure(String e);
}
