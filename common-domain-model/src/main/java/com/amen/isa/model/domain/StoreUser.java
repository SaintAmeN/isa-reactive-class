package com.amen.isa.model.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "store.user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StoreUser {

    @Id
    @EqualsAndHashCode.Include
    private String userId;

    private String firstName;
    private String lastName;

    private Basket basket;
}
