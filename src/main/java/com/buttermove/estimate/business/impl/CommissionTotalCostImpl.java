package com.buttermove.estimate.business.impl;

import com.buttermove.estimate.business.CommissionStrategicBusiness;
import com.buttermove.estimate.constant.EstimateConstant;

public class CommissionTotalCostImpl implements CommissionStrategicBusiness {
    @Override
    public double getTotalCost(String state, String type, int kilometers, Double amount, double normal, double premium) {
        double discount = 0.0;
        double commission = 0.0;
        double totalDiscount = 0.0;
        double ct = 0.0;
        if (type.equals(EstimateConstant.NORMAL_TYPE_BUSINESS)){
            commission = normal;
        } else {
            commission = premium;
        }
        if (kilometers > 26 && type.equals(EstimateConstant.NORMAL_TYPE_BUSINESS)){
            discount = 0.05;
        }
        if (kilometers > 25 && type.equals(EstimateConstant.PREMIUM_TYPE_BUSINESS)){
            discount = 0.05;
        }
        double cta = amount * commission;
        if (discount > 0){
            totalDiscount = cta * discount;
        }
        ct = cta - totalDiscount;

        return ct;
    }
}
