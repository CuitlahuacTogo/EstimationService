package com.buttermove.estimate.business;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
public class CommissionBusinessTest {
    CommissionBusiness commissionBusiness;

    @Test
    public void NY_Normal_Success(){
        String state = "NY";
        String type = "NORMAL";
        int kilometers = 20;
        Double amount = 100.0;
        double normal = 1.25;
        double premium = 0.0;
        commissionBusiness = new CommissionBusiness(state, type, kilometers, amount, normal, premium);

        Flux<Double> flux = Flux.just(commissionBusiness.getTotalCost())
                .log("");
        StepVerifier.create(flux)
                .expectNext(151.25)
                .verifyComplete();
    }
    @Test
    public void CA_Normal_Success(){
        String state = "CA";
        String type = "NORMAL";
        int kilometers = 20;
        Double amount = 100.0;
        double normal = 1.25;
        double premium = 0.0;
        commissionBusiness = new CommissionBusiness(state, type, kilometers, amount, normal, premium);

        Flux<Double> flux = Flux.just(commissionBusiness.getTotalCost())
                .log("");
        StepVerifier.create(flux)
                .expectNext(125.0)
                .verifyComplete();
    }
    @Test
    public void CA_Premium_Success(){
        String state = "CA";
        String type = "PREMIUM";
        int kilometers = 20;
        Double amount = 100.0;
        double normal = 1.25;
        double premium = 1.33;
        commissionBusiness = new CommissionBusiness(state, type, kilometers, amount, normal, premium);

        Flux<Double> flux = Flux.just(commissionBusiness.getTotalCost())
                .log("");
        StepVerifier.create(flux)
                .expectNext(133.0)
                .verifyComplete();
    }
    @Test
    public void CA_Normal_27KM_Success(){
        String state = "CA";
        String type = "NORMAL";
        int kilometers = 27;
        Double amount = 100.0;
        double normal = 1.23;
        double premium = 0.0;
        commissionBusiness = new CommissionBusiness(state, type, kilometers, amount, normal, premium);

        Flux<Double> flux = Flux.just(commissionBusiness.getTotalCost())
                .log("");
        StepVerifier.create(flux)
                .expectNext(116.85)
                .verifyComplete();
    }
    @Test
    public void CA_Premium_27KM_Success(){
        String state = "CA";
        String type = "PREMIUM";
        int kilometers = 27;
        Double amount = 100.0;
        double normal = 1.23;
        double premium = 1.33;
        commissionBusiness = new CommissionBusiness(state, type, kilometers, amount, normal, premium);

        Flux<Double> flux = Flux.just(commissionBusiness.getTotalCost())
                .log("");
        StepVerifier.create(flux)
                .expectNext(126.35)
                .verifyComplete();
    }
    @Test
    public void TX_Normal_10KM_Success(){
        String state = "TX";
        String type = "NORMAL";
        int kilometers = 10;
        Double amount = 100.0;
        double normal = 1.18;
        double premium = 1.28;
        commissionBusiness = new CommissionBusiness(state, type, kilometers, amount, normal, premium);

        Flux<Double> flux = Flux.just(commissionBusiness.getTotalCost())
                .log("");
        StepVerifier.create(flux)
                .expectNext(118.0)
                .verifyComplete();
    }
    @Test
    public void TX_Premium_10KM_Success(){
        String state = "TX";
        String type = "PREMIUM";
        int kilometers = 10;
        Double amount = 100.0;
        double normal = 1.18;
        double premium = 1.28;
        commissionBusiness = new CommissionBusiness(state, type, kilometers, amount, normal, premium);

        Flux<Double> flux = Flux.just(commissionBusiness.getTotalCost())
                .log("");
        StepVerifier.create(flux)
                .expectNext(128.0)
                .verifyComplete();
    }
    @Test
    public void TX_Normal_20KM_Success(){
        String state = "TX";
        String type = "NORMAL";
        int kilometers = 20;
        Double amount = 100.0;
        double normal = 1.18;
        double premium = 1.28;
        commissionBusiness = new CommissionBusiness(state, type, kilometers, amount, normal, premium);

        Flux<Double> flux = Flux.just(commissionBusiness.getTotalCost())
                .log("");
        StepVerifier.create(flux)
                .expectNext(114.46)
                .verifyComplete();
    }
    @Test
    public void TX_Premium_20KM_Success(){
        String state = "TX";
        String type = "PREMIUM";
        int kilometers = 20;
        Double amount = 100.0;
        double normal = 1.18;
        double premium = 1.28;
        commissionBusiness = new CommissionBusiness(state, type, kilometers, amount, normal, premium);

        Flux<Double> flux = Flux.just(commissionBusiness.getTotalCost())
                .log("");
        StepVerifier.create(flux)
                .expectNext(121.60000000000001)
                .verifyComplete();
    }

    @Test
    public void TX_Normal_31KM_Success(){
        String state = "TX";
        String type = "NORMAL";
        int kilometers = 31;
        Double amount = 100.0;
        double normal = 1.18;
        double premium = 1.28;
        commissionBusiness = new CommissionBusiness(state, type, kilometers, amount, normal, premium);

        Flux<Double> flux = Flux.just(commissionBusiness.getTotalCost())
                .log("");
        StepVerifier.create(flux)
                .expectNext(112.1)
                .verifyComplete();
    }

    @Test
    public void TX_Premium_31KM_Success(){
        String state = "TX";
        String type = "PREMIUM";
        int kilometers = 31;
        Double amount = 100.0;
        double normal = 1.18;
        double premium = 1.28;
        commissionBusiness = new CommissionBusiness(state, type, kilometers, amount, normal, premium);

        Flux<Double> flux = Flux.just(commissionBusiness.getTotalCost())
                .log("");
        StepVerifier.create(flux)
                .expectNext(121.6)
                .verifyComplete();
    }
}
