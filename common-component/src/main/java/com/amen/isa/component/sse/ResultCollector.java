package com.amen.isa.component.sse;

import com.amen.isa.component.controller.CollectClient;
import com.amen.isa.component.exception.CustomException;
import com.amen.isa.model.response.NonPrimitiveResponse;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
public class ResultCollector<T> {

    private static WebClient createWebClient(String baseUri) {
        HttpClient httpClient = HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2_000) // millis
                .doOnConnected(connection -> connection.addHandlerLast(new ReadTimeoutHandler(2)) // seconds
                        .addHandlerLast(new WriteTimeoutHandler(2))); //seconds

        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient)).baseUrl(baseUri).build();
    }

    public static Flux<NonPrimitiveResponse> createClient(String baseUri) {
        return new CollectClient(createWebClient(baseUri)).fetch();
    }

    public static Flux<Flux<NonPrimitiveResponse>> getCollectCalls() {
        return Flux.just(createClient("http://localhost:8081"), createClient("http://localhost:8082"), createClient("http://localhost:8083"));
    }

    // Play with:
    //  - error consuming
    //  - retries
    //  - parallelism
    //  - backpressure
    //  - tuple collecting
    public Flux<CallResult<T>> collect(Flux<Flux<T>> calls) {
        return calls
                .parallel()
                .flatMap(callFlux -> callFlux
                                 .flatMap(result -> Flux.just(new CallResult<T>(result, null)))
                                 // onError
                                 //  recovery ->
                                 //          onErrorResume
                                 //          onErrorReturn
                                 //          onErrorContinue
                                 .onErrorContinue((throwable, o) -> {
                                     log.info("OnErrorContinue o: {}", o.getClass());
                                     log.info("OnErrorContinue o: {}", o);
//                            log.info("OnErrorContinue: {}", , throwable);
                                 })
//                        .onErrorResume(throwable -> true, throwable -> {
//                            log.info("OnErrorResume: {}", throwable, throwable);
//                            return Flux.just(new CallResult<T>(null, throwable.getMessage()));
//                        })
                         // retry only on certain error
                        .retryWhen(Retry.backoff(50L, Duration.of(1L, ChronoUnit.SECONDS))
                                           .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> new CustomException()))
                )
                .sequential()
                .onErrorContinue((throwable, o) -> {
                    log.info("OnErrorContinue o: {}", o.getClass());
                    log.info("OnErrorContinue o: {}", o);
//                            log.info("OnErrorContinue: {}", , throwable);
                });
//                .onErrorResume(throwable -> true, throwable -> {
//                    log.info("OnErrorResume: {}", throwable, throwable);
//                    return Flux.just(new CallResult<T>(null, throwable.getMessage()));
//                });
    }

    public Flux<CallResult<T>> collectSingle(Flux<T> callFlux) {
        return callFlux.flatMap(result -> Flux.just(new CallResult<T>(result, null)))
//                .doOnError(throwable -> {
//                    log.info("Error happened:T {}", throwable, throwable);
//                })
                .onErrorContinue((throwable, o) -> {
                    log.info("ErrorT happened: {}", throwable, throwable);
                })
                .retryWhen(Retry.backoff(50L, Duration.of(1L, ChronoUnit.SECONDS)));
    }

    public Flux<Tuple3<CallResult<T>, CallResult<T>, CallResult<T>>> zipCollect() {
        return Flux.zip(collectSingle((Flux<T>) createClient("http://localhost:8081")),
                        collectSingle((Flux<T>) createClient("http://localhost:8082")),
                        collectSingle((Flux<T>) createClient("http://localhost:8083")));
    }

    public record CallResult<T>(T result, String errorMessage) {
    }
}
