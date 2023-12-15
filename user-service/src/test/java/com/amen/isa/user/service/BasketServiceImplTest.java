package com.amen.isa.user.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static reactor.core.scheduler.Schedulers.DEFAULT_BOUNDED_ELASTIC_QUEUESIZE;

@Slf4j
class BasketServiceImplTest {

    @Test
    void parallelizeRangeBuffer() {
        Flux.range(1, 10)
//                .buffer(3)
                .parallel()
                .runOn(Schedulers.parallel())
                .map(integer -> {
                    log.info("Start " + integer);
                    try {
                        Thread.sleep(1000 + new Random().nextInt(1000));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("End " + integer);
                    return integer;
                })
                .subscribe(integer -> {

                });


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void parallelizeRange() {
        Flux.range(1, 10)
//                .buffer(3)
                .parallel()
                .runOn(Schedulers.newBoundedElastic(3, DEFAULT_BOUNDED_ELASTIC_QUEUESIZE, "isa"))
                .map(integer -> {
                    log.info("Start " + integer);
                    try {
                        Thread.sleep(1000 + new Random().nextInt(1000));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("End " + integer);
                    return integer;
                })
                .subscribe(integer -> {

                });


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}