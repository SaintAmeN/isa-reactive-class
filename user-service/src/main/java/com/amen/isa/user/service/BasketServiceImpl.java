package com.amen.isa.user.service;

import com.amen.isa.component.client.ProductServiceClient;
import com.amen.isa.component.repository.UserRepository;
import com.amen.isa.model.contract.IBasketService;
import com.amen.isa.model.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.ContextView;

import java.util.List;
import java.util.Random;

// TODO: Write me :)
@Slf4j
@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements IBasketService {
    private final UserRepository userRepository;
    private final ProductServiceClient productServiceClient;

    @Override
    public Mono<Basket> getBasket(String userId) {
        return userRepository.findById(userId)
                .map(su -> su.getBasket());
    }

    @Override
    public Mono<Basket> addToBasket(String userId, BasketPosition position) {
        return userRepository.findById(userId)
                .flatMap(storeUser -> {
                    storeUser.getBasket().getPositions().add(position);

                    return userRepository.save(storeUser);
                })
                .map(StoreUser::getBasket);
    }

    //    @Override
//    public Mono<Basket> addToBasket(String userId, BasketPosition position) {
//        return userRepository.findById(userId)
//                .flatMap(storeUser -> {
//                    return Mono.zip(
//                            Mono.defer(() -> Mono.just(storeUser.getBasket().getCreated())),
//                            Mono.defer(() -> Mono.just(storeUser.getBasket().getName())),
//                })
//
//    }

    public Mono<Basket> addToBasket(String userId, Long productId) {
        return userRepository.findById(userId)
                .flatMap(storeUser -> {
                    log.info("User is found");
                    return Mono.zip(
                                    productServiceClient.getProductById(productId).map(Product::getUnit),
                                    productServiceClient.getProductById(productId).map(Product::getName))
                            .map(tupleResult -> {
                                log.info("Tuple collected");
                                return new BasketPosition(0,
                                                          new Product(0L, tupleResult.getT2(), 1, tupleResult.getT1()),
                                                          new ProductQuantity(10.0, 1, tupleResult.getT1()));
                            })
                            .flatMap(basketPosition -> {
                                log.info("Tuple collected");
                                storeUser.getBasket().getPositions().add(basketPosition);
                                return userRepository.save(storeUser);
                            })
                            .map(storeUser1 -> {
                                return storeUser1.getBasket();
                            });
                });
    }

    public Mono<Basket> addToBasket(String userId, List<Long> productIds) {
        return userRepository.findById(userId)
                .flatMap(storeUser -> {
                    return userRepository.save(storeUser);
                })
                .map(StoreUser::getBasket);
    }

    // z basket'u chcemy wydobyć dwie informacje
    //  - name
    //  - created

    @Override
    public Mono<Basket> removeFromBasket(String userId, int position) {
        return null;
    }
}