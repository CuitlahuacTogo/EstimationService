package com.buttermove.estimate.service.impl;

import com.buttermove.estimate.entity.State;
import com.buttermove.estimate.exception.EstimateException;
import com.buttermove.estimate.exception.StateException;
import com.buttermove.estimate.exception.TypeException;
import com.buttermove.estimate.repository.StateRepository;
import com.buttermove.estimate.service.EstimateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class EstimateServiceImplTest {

    @Autowired
    EstimateService estimateService;

    @Autowired
    StateRepository stateRepository;

    @Before
    public void setUp() {
        List<State> stateList = Arrays.asList(
                new State(null, "ny", 1.25, 1.35)
                , new State(null, "ca",1.23, 1.33)
                , new State(null, "az", 1.20, 1.30)
                , new State(null, "tx", 1.18, 1.28)
                , new State(null, "oh", 1.15, 1.25)
        );
        stateRepository.deleteAll()
                .thenMany(Flux.fromIterable(stateList))
                .flatMap(stateRepository::save)
                .doOnNext((item -> System.out.println("Inserted item is: " + item)))
                .blockLast();
    }

    @Test
    public void getEstimateResponseAccepted(){
        String ipClient = "172.8.9.28";
        String state = "NY";
        String type = "NORMAL";
        int kilometers = 10;
        Double amount = 100.0;
        StepVerifier.create(estimateService.getEstimateResponse(ipClient, state, type, kilometers, amount))
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void getEstimateResponse_WrongIP(){
        String ipClient = "172.8.9.28a";
        String state = "NY";
        String type = "NORMAL";
        int kilometers = 10;
        Double amount = 100.0;
        StepVerifier.create(estimateService.getEstimateResponse(ipClient, state, type, kilometers, amount))
                .expectSubscription()
                .expectErrorMatches( throwable -> throwable instanceof EstimateException).verify();
    }

    @Test
    public void getEstimateResponse_WrongState(){
        String ipClient = "172.8.9.28";
        String state = "NYa";
        String type = "NORMAL";
        int kilometers = 10;
        Double amount = 100.0;
        StepVerifier.create(estimateService.getEstimateResponse(ipClient, state, type, kilometers, amount))
                .expectSubscription()
                .expectErrorMatches( throwable -> throwable instanceof StateException).verify();
    }

    @Test
    public void getEstimateResponse_WrongType(){
        String ipClient = "172.8.9.28";
        String state = "NY";
        String type = "NORMALa";
        int kilometers = 10;
        Double amount = 100.0;
        StepVerifier.create(estimateService.getEstimateResponse(ipClient, state, type, kilometers, amount))
                .expectSubscription()
                .expectErrorMatches( throwable -> throwable instanceof TypeException).verify();
    }
}
