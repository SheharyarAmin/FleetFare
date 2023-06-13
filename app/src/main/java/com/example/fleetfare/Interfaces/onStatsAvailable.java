package com.example.fleetfare.Interfaces;

import com.example.fleetfare.Models.Stats;

public interface onStatsAvailable {
    void onSuccess(Stats stats);
    void onFailure(String s);
}
