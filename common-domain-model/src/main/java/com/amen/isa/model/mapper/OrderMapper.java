package com.amen.isa.model.mapper;

import com.amen.isa.model.domain.StoreOrder;
import com.amen.isa.model.domain.StoreUser;
import com.amen.isa.model.request.StoreOrderRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "created", expression = "java(null)")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "items", source = "request.items")
    StoreOrder requestToStoreOrder(StoreOrderRequest request, StoreUser user);
}
