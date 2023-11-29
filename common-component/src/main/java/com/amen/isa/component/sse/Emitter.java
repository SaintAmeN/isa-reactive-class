package com.amen.isa.component.sse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;


@Slf4j
@Getter
@RequiredArgsConstructor
public class Emitter<T> {
    private final List<Consumer<T>> consumers = new CopyOnWriteArrayList<>();

    public void registerConsumer(Consumer<T> consumer) {
        consumers.add(consumer);
        log.info("New consumer registered. Total number of consumers: " + consumers.size() + ".");
    }

    public void emit(T data) {
        consumers.forEach(consumer -> consumer.accept(data));
    }

    public void emit(Mono<T> data) {
        consumers.forEach(consumer -> consumer.accept(data.block()));
    }

    public void unregisterConsumer(Consumer<T> consumer) {
        consumers.remove(consumer);
        log.info("Consumer broke the connection, unregistering. Total number of consumers: " + consumers.size() + ".");
    }

    public static <T> Flux<T> streamFlux(Emitter<T> emitter) {
        return Flux.create(sink -> {
            Consumer<T> consumer = sink::next;
            emitter.registerConsumer(consumer);
            sink.onDispose(() -> {
                log.info("Disposing of listener.");
                emitter.unregisterConsumer(consumer);
            });
            sink.onCancel(() -> {
                log.info("Listener has been canceled.");
                emitter.unregisterConsumer(consumer);
            });
        });
    }

}