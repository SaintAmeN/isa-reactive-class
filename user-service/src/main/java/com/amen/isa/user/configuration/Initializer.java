package com.amen.isa.user.configuration;

import com.amen.isa.model.domain.StoreUser;
import com.mongodb.BasicDBObject;
import com.mongodb.reactivestreams.client.MongoClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class Initializer {
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        log.info("Init");
//        reactiveMongoTemplate.collectionExists(StoreUser.class).subscribe(exists -> {
//            log.info("Exists: {}", exists);
//            if (!exists) {
//                log.info("Not exists collection");
//                reactiveMongoTemplate.createCollection(StoreUser.class, CollectionOptions.empty().capped().size(100).maxDocuments(20))
//                        .subscribe(documentMongoCollection -> {
//                            log.info("Created collection");
//                        });
//            }
//        });

    }
}
