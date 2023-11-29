package com.amen.isa;

import com.amen.isa.component.client.ProductServiceClient;
import com.amen.isa.component.client.UserServiceClient;
import com.amen.isa.component.repository.OrderRepository;
import com.amen.isa.model.domain.MeasureUnit;
import com.amen.isa.model.domain.ProductQuantity;
import com.amen.isa.model.domain.StoreOrder;
import com.amen.isa.model.domain.StoreOrderItem;
import com.amen.isa.order.OrderServiceApplication;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = OrderServiceApplication.class)
@ExtendWith(SpringExtension.class)
class StoreOrderDataGenerator {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private ProductServiceClient productServiceClient;

    @Test
    void generateOrders() {
        var allUsers = userServiceClient.getAllBlocking();
        var allProducts = productServiceClient.getAllBlocking();

        for (int i = 0; i < 3; i++) {
            var randomUser = allUsers.get(new Random().nextInt(allUsers.size()));
            var randomOrders = Stream.generate(() -> {
                var randomProductId = allProducts.get(new Random().nextInt(allProducts.size())).getId();
                var randomUnit = new Random().nextInt(10) > 5 ? MeasureUnit.UNIT : MeasureUnit.GRAM;
                return new StoreOrderItem(randomProductId,
                                          new ProductQuantity(new Random().nextInt(1000) + 1, new Random().nextInt(100) + 2, randomUnit));
            }).limit(new Random().nextInt(10)).collect(Collectors.toSet());

            orderRepository.save(new StoreOrder(null, "1", new ObjectId(randomUser.getUserId()), randomOrders, LocalDateTime.now())).log().block();
        }
    }
}