package com.buttermove.estimate.repository;

import com.buttermove.estimate.entity.State;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface StateRepository extends ReactiveMongoRepository<State, String> {
    Mono<State> findStateByState(String state);
    Mono<Long> countStateByState(String state);
}
