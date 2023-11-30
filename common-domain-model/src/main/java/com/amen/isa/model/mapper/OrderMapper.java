package com.amen.isa.model.mapper;

import com.amen.isa.model.domain.StoreOrder;
import com.amen.isa.model.domain.StoreUser;
import com.amen.isa.model.request.StoreOrderRequest;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {ObjectId.class})
public interface OrderMapper {
    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "created", expression = "java(null)")
    @Mapping(target = "userId", expression = "java(new ObjectId(user.getUserId()))")
    @Mapping(target = "items", source = "request.items")
    StoreOrder requestToStoreOrder(StoreOrderRequest request, StoreUser user);
}
