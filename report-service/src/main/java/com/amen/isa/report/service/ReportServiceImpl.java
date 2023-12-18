package com.amen.isa.report.service;

import com.amen.isa.component.client.OrderServiceClient;
import com.amen.isa.component.client.ProductServiceClient;
import com.amen.isa.component.client.ShipmentServiceClient;
import com.amen.isa.model.contract.IReportService;
import com.amen.isa.model.domain.Product;
import com.amen.isa.model.domain.Report;
import com.amen.isa.model.domain.StoreOrder;
import com.amen.isa.model.response.ShipmentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// TODO: Write me :)
@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements IReportService {
    private final OrderServiceClient orderServiceClient;
    private final ProductServiceClient productServiceClient;
    private final ShipmentServiceClient shipmentServiceClient;

    @Override
    public Mono<Report> generateReport(String storeId) {
        return null;
    }

    // wersja 1
    //    @Override
//    public Mono<Report> generateReport(@NonNull LocalDateTime from,
//                                       @NonNull LocalDateTime to) {
//        if (!from.isBefore(to)) {
//            throw new IllegalArgumentException("IAE");
//        }
//
//        Map<Long, Product> products = new ConcurrentHashMap<>();
//
//        AtomicLong sales = new AtomicLong(0);
//        AtomicLong cost = new AtomicLong(0);
//
//        return orderServiceClient.fetchOrdersBetweenDates(from, to)
//                .map(storeOrder -> sumSales(storeOrder, sales))
//                .then(shipmentServiceClient
//                              .fetchShipmentsBetweenDates(from, to)
//                              .map(shipmentResponse -> getFromMap(shipmentResponse, products))
//                              .map(productMono -> productMono.mapNotNull(product -> sumCost(product, cost)))
//                              .then())
//                .then(Mono.just(Report.builder().totalCost(cost.get()).totalSales(sales.get()).build()));
//    }

    // wersja 2
//    public Mono<Report> generateReport(@NonNull LocalDateTime from,
//                                       @NonNull LocalDateTime to) {
//        if (!from.isBefore(to)) {
//            throw new IllegalArgumentException("IAE");
//        }
//
//        return shipmentServiceClient.fetchShipmentsBetweenDates(from, to)
//                .flatMap(shipmentResponse -> productServiceClient.getProductById(shipmentResponse.getProductId()))
//                .map(Product::getQuantity)
//                .reduce(Double::sum)
//                .flatMap(cost -> {
//                    return orderServiceClient.fetchOrdersBetweenDates(from, to)
//                            .map(this::sumSales)
//                            .reduce(Double::sum)
//                            .map(sumSales -> Report.builder().totalCost(cost).totalSales(sumSales).build());
//                });
//    }

    // wersja 3
    public Mono<Report> generateReport(@NonNull LocalDateTime from,
                                       @NonNull LocalDateTime to) {
        if (!from.isBefore(to)) {
            throw new IllegalArgumentException("IAE");
        }

        return Mono.zip(
                        calculateCost(from, to),
                        calculateSales(from, to)
                )
                .map(tuple -> Report.builder().totalCost(tuple.getT1()).totalSales(tuple.getT2()).build());
    }

    private Mono<Double> calculateCost(@NonNull LocalDateTime from,
                                       @NonNull LocalDateTime to) {
        return shipmentServiceClient.fetchShipmentsBetweenDates(from, to)
                .flatMap(shipmentResponse -> productServiceClient.getProductById(shipmentResponse.getProductId()))
                .map(Product::getQuantity)
                .reduce(Double::sum);
    }

    private Mono<Double> calculateSales(@NonNull LocalDateTime from,
                                        @NonNull LocalDateTime to) {
        return orderServiceClient.fetchOrdersBetweenDates(from, to)
                .map(this::sumSales)
                .reduce(Double::sum);
    }

    private Mono<Product> getFromMap(ShipmentResponse shipment, Map<Long, Product> memory) {
        return Mono.just(memory.get(shipment.getProductId()))
                .switchIfEmpty(
                        productServiceClient
                                .getProductById(shipment.getProductId())
                                .mapNotNull(product -> memory.put(shipment.getProductId(), product)));
    }

    private ShipmentResponse sumCost(Product product, AtomicLong cost) {
        return null;
    }

    private Double sumSales(StoreOrder storeOrder) {
        return storeOrder
                .getItems()
                .stream()
                .mapToDouble(value -> value.quantity().totalPrice() * value.quantity().quantity())
                .sum();
    }

    private StoreOrder sumSales(StoreOrder storeOrder, AtomicLong sales) {
        sales.getAndAdd(
                (long) storeOrder
                        .getItems()
                        .stream()
                        .mapToDouble(value -> value.quantity().totalPrice() * value.quantity().quantity()).sum() * 100);

        return storeOrder;
    }
}
