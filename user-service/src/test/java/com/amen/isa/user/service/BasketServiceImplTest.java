package com.amen.isa.user.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

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

    @Test
    void testContext(){
        // Start a Mono with a specific context value
        Mono<String> monoWithContext = Mono.deferContextual(ctx -> {
            String value = ctx.getOrDefault("initialKey", "DefaultValue");
            log.info("Initial Value in Context: " + value);

            // Perform some asynchronous or reactive operation here
            return Mono.just("Result from Mono").contextWrite(Context.of("initialKey", value));
        }).contextWrite(Context.of("initialKey", "CustomValue"));

        // Subscribe to the Mono
        monoWithContext.contextCapture().subscribe(result -> {
            log.info("Result: " + result);

            // Access the context within the subscriber
            String finalValue = Mono.deferContextual(ctx -> Mono.just(String.valueOf(ctx.get("initialKey")))).block();
            log.info("Final Value in Context: " + finalValue);
        });
    }
    @Test
    void testContext2() throws InterruptedException {
        {
            // Creating a Mono with a specific context
            Mono<String> monoWithContext = Mono.deferContextual(ctx -> {
                String initialValue = ctx.getOrDefault("initialKey", "DefaultValue");
                System.out.println("Initial Value in Context: " + initialValue);

                // Perform some asynchronous or reactive operation here
                return Mono.just("Result from Mono");
            }).contextWrite(Context.of("initialKey", "CustomValue"));

            // Subscribe to the Mono
            monoWithContext.subscribe(result -> System.out.println("Result: " + result));
            Thread.sleep(1000);
        }
    }
}