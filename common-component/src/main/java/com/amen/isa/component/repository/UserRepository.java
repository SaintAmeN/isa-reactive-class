package com.amen.isa.component.repository;

import com.amen.isa.model.domain.StoreUser;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveMongoRepository<StoreUser, String> {

    @Tailable
    Flux<String> findAllByFirstNameContaining(String firstNameContaining);
}
