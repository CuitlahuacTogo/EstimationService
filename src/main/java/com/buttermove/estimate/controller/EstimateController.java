package com.buttermove.estimate.controller;

import com.buttermove.estimate.constant.EstimateConstant;
import com.buttermove.estimate.exception.EstimateException;
import com.buttermove.estimate.exception.StateException;
import com.buttermove.estimate.exception.TypeException;
import com.buttermove.estimate.service.EstimateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController()
public class EstimateController {

    @Autowired
    EstimateService estimateService;

    @GetMapping(path = EstimateConstant.ESTIMATE_POINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> createEstimateException(
            @RequestParam(name = "state") String state,
            @RequestParam(name = "type") String type,
            @RequestParam(name = "kilometers") int kilometers,
            @RequestParam(name = "amount") Double amount,
            @RequestHeader("ip-client") String ipClient) {
                    return this.estimateService.getEstimateResponse(ipClient, state, type, kilometers, amount)
                    .map(Estimate -> new ResponseEntity<>(Estimate, HttpStatus.OK))
                    .onErrorReturn(EstimateException.class, new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED))
                    .onErrorReturn(StateException.class, ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"Unsupported state\"}"))
                    .onErrorReturn(TypeException.class, ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"Unsupported type\"}"))
                    .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NO_CONTENT));

    }
}
