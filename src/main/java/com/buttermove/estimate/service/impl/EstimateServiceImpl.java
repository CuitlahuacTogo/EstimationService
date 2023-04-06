package com.buttermove.estimate.service.impl;

import com.buttermove.estimate.business.CommissionBusiness;
import com.buttermove.estimate.constant.EstimateConstant;
import com.buttermove.estimate.dto.EstimateResponseDto;
import com.buttermove.estimate.entity.State;
import com.buttermove.estimate.exception.EstimateException;
import com.buttermove.estimate.exception.StateException;
import com.buttermove.estimate.exception.TypeException;
import com.buttermove.estimate.repository.StateRepository;
import com.buttermove.estimate.service.EstimateService;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Service
public class EstimateServiceImpl implements EstimateService {
    @Autowired
    StateRepository stateRepository;

    double normal;
    double premium;

    @Override
    public Mono<String> getEstimateResponse(String ipClient, String state, String type, int kilometers, Double amount){
        return preValidations(ipClient, state, type).
                switchIfEmpty(getState(state).flatMap( s -> {
                    return workWithEstimate(state, type, kilometers, amount, s.getNormal(), s.getPremium());
                }));
    }

    private Mono<String> preValidations(String ipClient, String state, String type){
        boolean a = validateIp(ipClient);
        if (!a) {
            return Mono.error(new EstimateException());
        }

        return validateState(state).switchIfEmpty(validateType(type.toUpperCase()));
    }

    private boolean validateIp(String ipClient){
        InetAddressValidator validator = InetAddressValidator.getInstance();
        return validator.isValidInet4Address(ipClient) || validator.isValidInet6Address(ipClient);
    }

    private Mono<String> validateState(String state){
        return stateRepository.countStateByState(state.toLowerCase()).flatMap(s -> {
            if (s <= 0){
                return Mono.error(new StateException());
            }
            return Mono.empty();
        });
    }

    private Mono<String> validateType(String type){
        if (!(type.equals(EstimateConstant.NORMAL_TYPE_BUSINESS) || type.equals(EstimateConstant.PREMIUM_TYPE_BUSINESS))){
            return Mono.error(new TypeException());
        }
        return Mono.empty();
    }

    private Mono<State> getState(String state){
        return stateRepository.findStateByState(state.toLowerCase());
    }

    private Mono<String> workWithEstimate(String state, String type, int kilometers, Double amount, double normal, double premium){
        EstimateResponseDto estimateResponse = new EstimateResponseDto();
        estimateResponse.setTotalCost(new CommissionBusiness(state.toUpperCase(), type, kilometers, amount, normal, premium).getTotalCost());
        estimateResponse.setDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(new Date()));
        return Mono.just(String.format("{\"total_cost\": %.2f, \"date\": \"%s\"}", estimateResponse.getTotalCost(), estimateResponse.getDate()));
    }
}
