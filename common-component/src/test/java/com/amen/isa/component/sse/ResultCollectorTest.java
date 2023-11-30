package com.amen.isa.component.sse;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResultCollectorTest {

    @Test
    void testPublishOn() {
        var list = Flux.range(1, 10)
                .filter(i -> i % 2 == 0)
                .concatWith(Flux.fromIterable(List.of(1, 2, 3, 455, 55)))
                // ^^^^^^^^^
                .subscribeOn(Schedulers.boundedElastic())
                .publishOn(Schedulers.boundedElastic())
                .collectList().block();
                // \/ \/ \/

//        subscribe != subscribeOn

    }
}