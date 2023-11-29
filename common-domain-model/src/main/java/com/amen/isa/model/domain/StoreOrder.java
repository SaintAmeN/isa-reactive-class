package com.amen.isa.model.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "store.order")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StoreOrder {
    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String storeId;

    @DocumentReference
    private StoreUser user;

    private Set<StoreOrderItem> items;

    @CreationTimestamp
    private LocalDateTime created;
}
