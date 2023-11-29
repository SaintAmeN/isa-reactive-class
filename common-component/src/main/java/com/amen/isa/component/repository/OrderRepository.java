package com.amen.isa.component.repository;

import com.amen.isa.model.domain.StoreOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<StoreOrder, String> {
}
