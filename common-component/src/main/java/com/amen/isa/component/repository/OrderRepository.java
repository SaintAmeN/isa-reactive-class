package com.amen.isa.component.repository;

import com.amen.isa.model.domain.StoreOrder;
import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<StoreOrder, String> {
    @Query(value = "{ 'user': ?0 }")
    Flux<StoreOrder> findAllByUserId(ObjectId id);
}
