package com.buttermove.estimate.initialize;

import com.buttermove.estimate.entity.State;
import com.buttermove.estimate.repository.StateRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Component
@Profile({"!test & !prod"})
@Slf4j
public class DataInitialize implements CommandLineRunner {
    @Autowired
    StateRepository stateRepository;

//    public static final String DB_FORMAT_DATETIME = "yyyy-M-d HH:mm:ss";

    @Override
    public void run(String... args) {
        initialDataSetUp();
    }

    private void initialDataSetUp() {
        stateRepository.deleteAll()
                .thenMany(Flux.fromIterable(data()))
                .flatMap(stateRepository::save)
                .thenMany(stateRepository.findAll())
                .subscribe((item -> System.out.println("Item inserted from CommandLineRunner : " + item)));
    }

    @SneakyThrows
    private List<State> data() {
        return Arrays.asList(
                new State(null, "ny", 1.25, 1.35)
                , new State(null, "ca",1.23, 1.33)
                , new State(null, "az", 1.20, 1.30)
                , new State(null, "tx", 1.18, 1.28)
                , new State(null, "oh", 1.15, 1.25)
        );
    }
}
