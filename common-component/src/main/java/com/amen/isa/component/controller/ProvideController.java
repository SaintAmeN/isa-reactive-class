package com.amen.isa.component.controller;

import com.amen.isa.model.response.NonPrimitiveResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController()
@RequiredArgsConstructor
@RequestMapping("/provide")
public class ProvideController {
    @Value("${spring.application.name:INVALID}")
    private String applicationName;

    private AtomicInteger counter = new AtomicInteger(1);

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<NonPrimitiveResponse> provide() {
        return Flux.interval(Duration.of(1L, ChronoUnit.SECONDS))
                .map(aLong -> new NonPrimitiveResponse(applicationName, counter.getAndIncrement()));
    }

//    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Mono<NonPrimitiveResponse> provideMono() {
//        return Mono.empty();
//    }

//    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<NonPrimitiveResponse> batch() {
//        return Flux.interval(Duration.of(1L, ChronoUnit.SECONDS))
//                .map(aLong -> new NonPrimitiveResponse(applicationName, counter++));
//    }
}