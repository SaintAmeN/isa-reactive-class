package com.amen.isa.report.service;

import com.amen.isa.component.client.OrderServiceClient;
import com.amen.isa.component.client.ProductServiceClient;
import com.amen.isa.component.client.ShipmentServiceClient;
import com.amen.isa.component.exception.CustomException;
import com.amen.isa.model.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Executor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//

// serwis zbiera dane i przekazuje dalej
// serwis zbiera i obrabia i wysyła do mainframe
//
//    Scheduler wysyła trigger
//    wywołujemy metode @Async serwisu
//      w tasku robimy zapytanie do innych serwisów
//      krok 1 - odpytujemy o dane (kolekcja X)
//              jeden serwis
//      krok 2:
//        wykorzystuję informacje z krok 1 do odpytania wszystkich serwisów
//        dla każdego elementu odpytujemy 1/2 serwisy aby przemapować dane na docelowe
//
//      krok 3:
//        wysyłamy dane dalej ...
@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {
    private final OrderServiceClient orderServiceClient;
    private final ProductServiceClient productServiceClient;
    private final ShipmentServiceClient shipmentServiceClient;

//    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Async
    public void triggerJob() {
        // pobieramy listę orderów
        Flux<OrderResponse> orders = orderServiceClient.getOrders();

        // > List[Obj]
        // >> Mono List[Obj]
        // Mono list -> flux -> (retry) [nie działa na mono]
        orders
                .flatMap(orderResponse -> {
                    return Flux.fromIterable(orderResponse.items())
                            .flatMap(storeOrderItem -> {
                                log.info("Product requesting: {}", storeOrderItem.productId());
                                return productServiceClient.getProductById(storeOrderItem.productId());
                            });
                })
                .buffer(10)
                .flatMap(products -> {
                    log.info("We're here: {}", products);
                    return Mono.just(products);
                })
                .flatMap(products -> {
                    log.info("Triggering for: {}", products);
                    return shipmentServiceClient.submitShipments(products);
                })
//                .switchIfEmpty(Mono.error(CustomException::new))
                .onErrorContinue((throwable, o) -> {
                    log.error("Error on value: {}", o);
                })
//                .defaultIfEmpty(Mono.error(CustomException::new))
                .subscribe();
    }
}
