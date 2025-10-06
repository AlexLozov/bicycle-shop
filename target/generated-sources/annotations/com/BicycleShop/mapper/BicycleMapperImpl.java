package com.BicycleShop.mapper;

import com.BicycleShop.model.dto.bicycle.BicycleDTO;
import com.BicycleShop.model.entities.Bicycle;
import java.util.Objects;
import javax.annotation.processing.Generated;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-06T21:33:29+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class BicycleMapperImpl implements BicycleMapper {

    @Override
    public BicycleDTO toBicycleDTO(Bicycle bicycle) {
        if ( bicycle == null ) {
            return null;
        }

        BicycleDTO.BicycleDTOBuilder bicycleDTO = BicycleDTO.builder();

        bicycleDTO.id( bicycle.getId() );
        bicycleDTO.brand( bicycle.getBrand() );
        bicycleDTO.type( bicycle.getType() );
        bicycleDTO.price( bicycle.getPrice() );
        bicycleDTO.stock( bicycle.getStock() );
        bicycleDTO.description( bicycle.getDescription() );
        bicycleDTO.imageUrl( bicycle.getImageUrl() );
        bicycleDTO.createdAt( bicycle.getCreatedAt() );
        bicycleDTO.updatedAt( bicycle.getUpdatedAt() );

        return bicycleDTO.build();
    }
}
