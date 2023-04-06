package com.buttermove.estimate.service;

import com.buttermove.estimate.dto.EstimateResponseDto;
import reactor.core.publisher.Mono;

public interface EstimateService {
    Mono<String> getEstimateResponse(String ipClient, String state, String type, int kilometers, Double amount);
}
