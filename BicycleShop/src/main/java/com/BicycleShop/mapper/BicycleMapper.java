package com.BicycleShop.mapper;

import com.BicycleShop.model.dto.bicycle.BicycleDTO;
import com.BicycleShop.model.entities.Bicycle;
import com.BicycleShop.model.request.bicycle.BicycleRequest;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Objects;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {DateTimeUtils.class, Objects.class}
)
public interface BicycleMapper {
//      - сущность с базы             --дто
//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "brand", target = "brand")
//    @Mapping(source = "name", target = "name")
//    @Mapping(source = "type", target = "type")
//    @Mapping(source = "price", target = "price")
//    @Mapping(source = "stock", target = "stock")
//    @Mapping(source = "description", target = "description")
//    @Mapping(source = "imageUrl", target = "imageUrl")
//    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-DD'T'HH:mm:ss")
//    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-DD'T'HH:mm:ss")
    BicycleDTO toBicycleDTO(Bicycle bicycle);


    @Mapping(target = "id", ignore = true)
//    @Mapping(source = "brand", target = "brand")
//    @Mapping(source = "name", target = "name")
//    @Mapping(source = "type", target = "type")
//    @Mapping(source = "price", target = "price")
//    @Mapping(source = "stock", target = "stock")
//    @Mapping(source = "description", target = "description")
//    @Mapping(source = "imageUrl", target = "imageUrl")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Bicycle createBicycle(BicycleRequest bicycleRequest);



}
