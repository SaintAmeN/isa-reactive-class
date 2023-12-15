package com.amen.isa.report.service;

import com.amen.isa.component.client.OrderServiceClient;
import com.amen.isa.component.client.ProductServiceClient;
import com.amen.isa.component.client.ShipmentServiceClient;
import com.amen.isa.model.domain.*;
import com.amen.isa.model.response.ShipmentResponse;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// TODO: Feel me :)
@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private ProductServiceClient productServiceClient;

    @Mock
    private OrderServiceClient orderServiceClient;

    @Mock
    private ShipmentServiceClient shipmentServiceClient;

    @InjectMocks
    private ReportServiceImpl reportService;

    @Test
    void testGenerateReportHappyPath() {
        // Mock responses from clients
        when(productServiceClient.fetchAvailableProducts()).thenReturn(Flux.just(new Product(), new Product()));
        when(orderServiceClient.fetchOrdersBetweenDates(any(), any())).thenReturn(Flux.just(createMockOrder()));
        when(shipmentServiceClient.fetchShipmentsBetweenDates(any(), any())).thenReturn(Flux.just(createMockShipment()));

        // Call the service method
        Report report = reportService.generateReport(LocalDateTime.now().minusDays(7), LocalDateTime.now());

        // Assert the results
        assertNotNull(report);
        // Add more assertions based on your business logic
    }

    @ParameterizedTest
    @MethodSource("invalidDateRanges")
    void testGenerateReportInvalidDates(LocalDateTime startDate, LocalDateTime endDate) {
        // Call the service method and expect an exception
        assertThrows(IllegalArgumentException.class, () -> reportService.generateReport(startDate, endDate));
    }

    // Add more tests for error cases, e.g., missing products, orders, etc.

    private static Stream<Arguments> invalidDateRanges() {
        // Provide various invalid date ranges for parameterized testing
        return Stream.of(
                Arguments.of(null, LocalDateTime.now()),
                Arguments.of(LocalDateTime.now(), null),
                Arguments.of(LocalDateTime.now(), LocalDateTime.now().minusDays(7)),
                Arguments.of(LocalDateTime.now(), LocalDateTime.now()),
                Arguments.of(LocalDateTime.now().plusDays(7), LocalDateTime.now())
        );
    }

    private StoreOrder createMockOrder() {
        return new StoreOrder("orderId", "storeId", new ObjectId("uid"), Set.of(createMockOrderItem()), LocalDateTime.now());
    }

    private StoreOrderItem createMockOrderItem() {
        return new StoreOrderItem(1L, new ProductQuantity(10.0, 2, MeasureUnit.UNIT));
    }

    private static ShipmentResponse createMockShipment() {
        return new ShipmentResponse(/*this needs to be changed once class is filled*/);
    }

    private ProductQuantity createMockProductQuantity() {
        return new ProductQuantity(100.0, 5, MeasureUnit.KILOGRAM);
    }

    @Test
    void testGenerateReportNoAvailableProducts() {
        // Mock responses from clients
        when(productServiceClient.fetchAvailableProducts()).thenReturn(Flux.empty());

        // Call the service method and expect an exception
        assertThrows(IllegalStateException.class, () -> reportService.generateReport(LocalDateTime.now().minusDays(7), LocalDateTime.now()));
    }

    @Test
    void testGenerateReportEmptyOrdersAndShipments() {
        // Mock responses from clients
        when(productServiceClient.fetchAvailableProducts()).thenReturn(Flux.just(new Product(), new Product()));
        when(orderServiceClient.fetchOrdersBetweenDates(any(), any())).thenReturn(Flux.empty());
        when(shipmentServiceClient.fetchShipmentsBetweenDates(any(), any())).thenReturn(Flux.empty());

        // Call the service method
        Report report = reportService.generateReport(LocalDateTime.now().minusDays(7), LocalDateTime.now());

        // Assert the results for scenarios with empty orders and shipments
        assertNotNull(report);
        // Add assertions for cases with no orders and shipments
    }

    @ParameterizedTest
    @MethodSource("generateReportTestData")
    void testGenerateReportWithDifferentData(Flux<StoreOrder> orders, Flux<ShipmentResponse> shipments, double expectedTotalSales, double expectedTotalCost) {
        // Mock responses from clients
        when(productServiceClient.fetchAvailableProducts()).thenReturn(Flux.just(new Product(), new Product()));
        when(orderServiceClient.fetchOrdersBetweenDates(any(), any())).thenReturn(orders);
        when(shipmentServiceClient.fetchShipmentsBetweenDates(any(), any())).thenReturn(shipments);

        // Call the service method
        Report report = reportService.generateReport(LocalDateTime.now().minusDays(7), LocalDateTime.now());

        // Assert the results
        assertNotNull(report);
        assertEquals(expectedTotalSales, report.getTotalSales(), 0.01);
        assertEquals(expectedTotalCost, report.getTotalCost(), 0.01);
        // Add more assertions based on your business logic
    }

    private static StoreOrder createMockStoreOrder(Long productId) {
        return new StoreOrder("orderId", "storeId", new ObjectId("userId"), Set.of(createMockStoreOrderItem(productId)), LocalDateTime.now());
    }

    private static StoreOrderItem createMockStoreOrderItem(Long productId) {
        return StoreOrderItem.builder().productId(productId).quantity(new ProductQuantity(10.0, 2, MeasureUnit.UNIT)).build();
    }

    private static Stream<Arguments> generateReportTestData() {
        // Test Case 1: Normal scenario with orders and shipments
        Flux<StoreOrder> orders1 = Flux.just(createMockStoreOrder(1L), createMockStoreOrder(2L));
        Flux<ShipmentResponse> shipments1 = Flux.just(createMockShipment(), createMockShipment());
        double expectedTotalSales1 = 2000.0;
        double expectedTotalCost1 = 1000.0;

        // Test Case 2: No orders and shipments
        Flux<StoreOrder> orders2 = Flux.empty();
        Flux<ShipmentResponse> shipments2 = Flux.empty();
        double expectedTotalSales2 = 0.0;
        double expectedTotalCost2 = 0.0;

        // Test Case 3: Complex scenario with different quantities and prices
        Flux<StoreOrder> orders3 = Flux.just(createMockStoreOrderWithQuantityAndPrice(1L, 5, 50.0), createMockStoreOrderWithQuantityAndPrice(2L, 10, 30.0));
        Flux<ShipmentResponse> shipments3 = Flux.just(createMockShipmentWithProductQuantity(5, 20.0), createMockShipmentWithProductQuantity(10, 15.0));
        double expectedTotalSales3 = 800.0; // (5 * 50.0) + (10 * 30.0)
        double expectedTotalCost3 = 300.0; // (5 * 50.0) + (10 * 15.0)

        return Stream.of(
                Arguments.of(orders1, shipments1, expectedTotalSales1, expectedTotalCost1),
                Arguments.of(orders2, shipments2, expectedTotalSales2, expectedTotalCost2),
                Arguments.of(orders3, shipments3, expectedTotalSales3, expectedTotalCost3)
        );
    }

    private static StoreOrder createMockStoreOrderWithQuantityAndPrice(Long productId, int quantity, double totalPrice) {
        return new StoreOrder("orderId", "storeId", new ObjectId("userId"), Set.of(createMockStoreOrderItemWithQuantityAndPrice(productId, quantity, totalPrice)),
                              LocalDateTime.now());
    }

    private static StoreOrderItem createMockStoreOrderItemWithQuantityAndPrice(Long productId, int quantity, double totalPrice) {
        return StoreOrderItem.builder().productId(productId).quantity(new ProductQuantity(totalPrice, quantity, MeasureUnit.UNIT)).build();
    }

    private static ShipmentResponse createMockShipmentWithProductQuantity(int quantity, double totalPrice) {
        return new ShipmentResponse(/* TODO implement*/);
    }
}