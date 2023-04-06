package com.buttermove.estimate.business.impl;

import com.buttermove.estimate.business.CommissionStrategicBusiness;

public class CommissionWithIvaImpl implements CommissionStrategicBusiness {
    @Override
    public double getTotalCost(String state, String type, int kilometers, Double amount, double normal, double premium) {
        double iva = 0.21;
        double mba = amount * normal;
        double mbb = mba * iva;
        return mba + mbb;
    }
}
