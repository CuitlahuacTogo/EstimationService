package com.buttermove.estimate.business;

import com.buttermove.estimate.business.impl.CommissionBaseAmountImpl;
import com.buttermove.estimate.business.impl.CommissionTotalCostImpl;
import com.buttermove.estimate.business.impl.CommissionWithIvaImpl;
import com.buttermove.estimate.constant.EstimateConstant;

import java.util.Objects;

public class CommissionBusiness {

    private final double totalCost;

    CommissionStrategicBusiness commissionStrategicBusiness;

    public CommissionBusiness(String state, String type, int kilometers, Double amount, double normal, double premium){
        this.totalCost = getTotalCostAccepted(state, type, kilometers, amount, normal, premium);
    }

    public double getTotalCost(){ return totalCost; }

    private double getTotalCostAccepted(String state, String type, int kilometers, Double amount, double normal, double premium){
        if (isValidState(state.toUpperCase()) && isValidType(type.toUpperCase())){
            commissionStrategicBusiness = getStrategic(state);
//            assert commissionStrategicBusiness != null;
            return commissionStrategicBusiness.getTotalCost(state,type.toUpperCase(),kilometers,amount, normal, premium);
        }
        return 0.0;
    }

    private Boolean isValidState(String state){
        if (Objects.isNull(state)){
            return false;
        } else if (state.length() == 0){
            return false;
        } else return state.equals(EstimateConstant.NY_STATE_BUSINESS) ||
                state.equals(EstimateConstant.CA_STATE_BUSINESS) ||
                state.equals(EstimateConstant.AZ_STATE_BUSINESS) ||
                state.equals(EstimateConstant.TX_STATE_BUSINESS) ||
                state.equals(EstimateConstant.OH_STATE_BUSINESS);
    }

    private Boolean isValidType(String type){
        if (Objects.isNull(type)){
            return false;
        } else if (type.length() == 0){
            return false;
        } else return type.equals(EstimateConstant.NORMAL_TYPE_BUSINESS) ||
                type.equals(EstimateConstant.PREMIUM_TYPE_BUSINESS);
    }

    private CommissionStrategicBusiness getStrategic(String state){
        switch (state){
            case EstimateConstant.NY_STATE_BUSINESS:
                return new CommissionWithIvaImpl();
            case EstimateConstant.CA_STATE_BUSINESS:
            case EstimateConstant.AZ_STATE_BUSINESS:
                return new CommissionTotalCostImpl();
            case EstimateConstant.TX_STATE_BUSINESS:
            case EstimateConstant.OH_STATE_BUSINESS:
                return new CommissionBaseAmountImpl();
                default: return null;
        }
    }
}
