package com.amen.isa.model.domain;

import jakarta.persistence.Id;
import lombok.*;
import org.bson.types.ObjectId;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

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

    // @DocumentReference
    private ObjectId userId;

    private Set<StoreOrderItem> items;

    @CreationTimestamp
    private LocalDateTime created;
}
