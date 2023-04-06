package com.buttermove.estimate.controller;

import com.buttermove.estimate.constant.EstimateConstant;
import com.buttermove.estimate.entity.State;
import com.buttermove.estimate.repository.StateRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
@DirtiesContext
@ActiveProfiles("test")
public class EstimateControllerTest {

    @Autowired
    WebTestClient webTestClient;

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
                .doOnNext((item -> System.out.println("Inserted Item is :" + item)))
                .blockLast();
    }

    @Test
    public void getEstimateResponse_Ok() {
        webTestClient.get().uri( uriBuilder -> uriBuilder
                        .path(EstimateConstant.ESTIMATE_POINT)
                        .queryParam("state", "ny")
                        .queryParam("type", "normal")
                        .queryParam("kilometers", 10)
                        .queryParam("amount", 100.0)
                        .build()
                )
                .header("ip-client", "172.8.9.28")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult();
    }

    @Test
    public void getEstimateResponse_Precondition_Failed() {
        webTestClient.get().uri( uriBuilder -> uriBuilder
                        .path(EstimateConstant.ESTIMATE_POINT)
                        .queryParam("state", "ny")
                        .queryParam("type", "normal")
                        .queryParam("kilometers", 10)
                        .queryParam("amount", 100.0)
                        .build()
                )
                .header("ip-client", "172.8.9.28s")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .returnResult();
    }

    @Test
    public void getEstimateResponse_Unsupported_State() {
        webTestClient.get().uri( uriBuilder -> uriBuilder
                        .path(EstimateConstant.ESTIMATE_POINT)
                        .queryParam("state", "ny2")
                        .queryParam("type", "normal")
                        .queryParam("kilometers", 10)
                        .queryParam("amount", 100.0)
                        .build()
                )
                .header("ip-client", "172.8.9.28")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .returnResult();
    }

    @Test
    public void getEstimateResponse_Unsupported_Type() {
        webTestClient.get().uri( uriBuilder -> uriBuilder
                        .path(EstimateConstant.ESTIMATE_POINT)
                        .queryParam("state", "ny")
                        .queryParam("type", "normal2")
                        .queryParam("kilometers", 10)
                        .queryParam("amount", 100.0)
                        .build()
                )
                .header("ip-client", "172.8.9.28")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .returnResult();
    }

    @Test
    public void getEstimateResponse_NY_Normal_10K_Success() {
        webTestClient.get().uri( uriBuilder -> uriBuilder
                        .path(EstimateConstant.ESTIMATE_POINT)
                        .queryParam("state", "ny")
                        .queryParam("type", "normal")
                        .queryParam("kilometers", 10)
                        .queryParam("amount", 100.0)
                        .build()
                )
                .header("ip-client", "172.8.9.28")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total_cost").isEqualTo(151.25);
    }

    @Test
    public void getEstimateResponse_CA_Normal_10K_Success() {
        webTestClient.get().uri( uriBuilder -> uriBuilder
                        .path(EstimateConstant.ESTIMATE_POINT)
                        .queryParam("state", "ca")
                        .queryParam("type", "normal")
                        .queryParam("kilometers", 10)
                        .queryParam("amount", 100.0)
                        .build()
                )
                .header("ip-client", "172.8.9.28")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total_cost").isEqualTo(123.0);
    }

    @Test
    public void getEstimateResponse_CA_Normal_27K_Success() {
        webTestClient.get().uri( uriBuilder -> uriBuilder
                        .path(EstimateConstant.ESTIMATE_POINT)
                        .queryParam("state", "ca")
                        .queryParam("type", "normal")
                        .queryParam("kilometers", 27)
                        .queryParam("amount", 100.0)
                        .build()
                )
                .header("ip-client", "172.8.9.28")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total_cost").isEqualTo(116.85);
    }

    @Test
    public void getEstimateResponse_CA_Premium_10K_Success() {
        webTestClient.get().uri( uriBuilder -> uriBuilder
                        .path(EstimateConstant.ESTIMATE_POINT)
                        .queryParam("state", "ca")
                        .queryParam("type", "premium")
                        .queryParam("kilometers", 10)
                        .queryParam("amount", 100.0)
                        .build()
                )
                .header("ip-client", "172.8.9.28")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total_cost").isEqualTo(133.0);
    }

    @Test
    public void getEstimateResponse_CA_Premium_26K_Success() {
        webTestClient.get().uri( uriBuilder -> uriBuilder
                        .path(EstimateConstant.ESTIMATE_POINT)
                        .queryParam("state", "ca")
                        .queryParam("type", "premium")
                        .queryParam("kilometers", 26)
                        .queryParam("amount", 100.0)
                        .build()
                )
                .header("ip-client", "172.8.9.28")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total_cost").isEqualTo(126.35);
    }

    @Test
    public void getEstimateResponse_TX_Normal_10K_Success() {
        webTestClient.get().uri( uriBuilder -> uriBuilder
                        .path(EstimateConstant.ESTIMATE_POINT)
                        .queryParam("state", "tx")
                        .queryParam("type", "normal")
                        .queryParam("kilometers", 10)
                        .queryParam("amount", 100.0)
                        .build()
                )
                .header("ip-client", "172.8.9.28")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total_cost").isEqualTo(118.0);
    }

    @Test
    public void getEstimateResponse_TX_Normal_20K_Success() {
        webTestClient.get().uri( uriBuilder -> uriBuilder
                        .path(EstimateConstant.ESTIMATE_POINT)
                        .queryParam("state", "tx")
                        .queryParam("type", "normal")
                        .queryParam("kilometers", 20)
                        .queryParam("amount", 100.0)
                        .build()
                )
                .header("ip-client", "172.8.9.28")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total_cost").isEqualTo(114.46);
    }

    @Test
    public void getEstimateResponse_TX_Normal_31K_Success() {
        webTestClient.get().uri( uriBuilder -> uriBuilder
                        .path(EstimateConstant.ESTIMATE_POINT)
                        .queryParam("state", "tx")
                        .queryParam("type", "normal")
                        .queryParam("kilometers", 31)
                        .queryParam("amount", 100.0)
                        .build()
                )
                .header("ip-client", "172.8.9.28")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total_cost").isEqualTo(112.1);
    }

    @Test
    public void getEstimateResponse_TX_Premium_10K_Success() {
        webTestClient.get().uri( uriBuilder -> uriBuilder
                        .path(EstimateConstant.ESTIMATE_POINT)
                        .queryParam("state", "tx")
                        .queryParam("type", "premium")
                        .queryParam("kilometers", 10)
                        .queryParam("amount", 100.0)
                        .build()
                )
                .header("ip-client", "172.8.9.28")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total_cost").isEqualTo(128.0);
    }

    @Test
    public void getEstimateResponse_TX_Premium_20K_Success() {
        webTestClient.get().uri( uriBuilder -> uriBuilder
                        .path(EstimateConstant.ESTIMATE_POINT)
                        .queryParam("state", "tx")
                        .queryParam("type", "premium")
                        .queryParam("kilometers", 20)
                        .queryParam("amount", 100.0)
                        .build()
                )
                .header("ip-client", "172.8.9.28")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total_cost").isEqualTo(121.6);
    }

    @Test
    public void getEstimateResponse_TX_Premium_31K_Success() {
        webTestClient.get().uri( uriBuilder -> uriBuilder
                        .path(EstimateConstant.ESTIMATE_POINT)
                        .queryParam("state", "tx")
                        .queryParam("type", "premium")
                        .queryParam("kilometers", 31)
                        .queryParam("amount", 100.0)
                        .build()
                )
                .header("ip-client", "172.8.9.28")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total_cost").isEqualTo(121.6);
    }

}