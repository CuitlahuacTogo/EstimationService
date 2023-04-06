package com.buttermove.estimate.business.impl;

import com.buttermove.estimate.business.CommissionStrategicBusiness;
import com.buttermove.estimate.constant.EstimateConstant;

public class CommissionBaseAmountImpl implements CommissionStrategicBusiness {
    @Override
    public double getTotalCost(String state, String type, int kilometers, Double amount, double normal, double premium) {
        double discount = 1.0;
        double commission = 0.0;
        double mba = 0.0;
        double ct = 0.0;

        if (type.equals(EstimateConstant.NORMAL_TYPE_BUSINESS)){
            commission = normal;
        } else {
            commission = premium;
        }
        if(kilometers >= 20 && kilometers <= 30){
            if (type.equals(EstimateConstant.PREMIUM_TYPE_BUSINESS)){
                discount = 0.95;
            } else {
                discount = 0.97;
            }

            mba = amount * discount;
            ct = mba * commission;
        } else {
            if (kilometers > 30){
                discount = 0.95;
            }
            mba = amount * commission;
            ct = mba * discount;
        }

        return ct;
    }
}
