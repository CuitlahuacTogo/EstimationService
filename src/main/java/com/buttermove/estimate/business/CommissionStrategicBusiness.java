package com.buttermove.estimate.business;

public interface CommissionStrategicBusiness {
    double getTotalCost(String state, String type, int kilometers, Double amount, double normal, double premium);

}
