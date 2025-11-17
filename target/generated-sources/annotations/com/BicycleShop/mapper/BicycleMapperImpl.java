package com.BicycleShop.mapper;

import com.BicycleShop.model.dto.bicycle.BicycleDTO;
import com.BicycleShop.model.dto.bicycle.BicycleSearchDTO;
import com.BicycleShop.model.entities.Bicycle;
import com.BicycleShop.model.request.bicycle.NewBicycleRequest;
import com.BicycleShop.model.request.bicycle.UpdateBicycleRequest;
import java.util.Objects;
import javax.annotation.processing.Generated;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-03T12:02:36+0200",
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
        bicycleDTO.name( bicycle.getName() );
        bicycleDTO.brand( bicycle.getBrand() );
        bicycleDTO.type( bicycle.getType() );
        bicycleDTO.price( bicycle.getPrice() );
        bicycleDTO.stock( bicycle.getStock() );
        bicycleDTO.description( bicycle.getDescription() );
        bicycleDTO.imageUrl( bicycle.getImageUrl() );
        bicycleDTO.createdAt( bicycle.getCreatedAt() );

        return bicycleDTO.build();
    }

    @Override
    public Bicycle createBicycle(NewBicycleRequest newBicycleRequest) {
        if ( newBicycleRequest == null ) {
            return null;
        }

        Bicycle bicycle = new Bicycle();

        bicycle.setName( newBicycleRequest.getName() );
        bicycle.setBrand( newBicycleRequest.getBrand() );
        bicycle.setType( newBicycleRequest.getType() );
        bicycle.setPrice( newBicycleRequest.getPrice() );
        bicycle.setStock( newBicycleRequest.getStock() );
        bicycle.setDescription( newBicycleRequest.getDescription() );
        bicycle.setImageUrl( newBicycleRequest.getImageUrl() );

        return bicycle;
    }

    @Override
    public void updateBicycle(Bicycle bicycle, UpdateBicycleRequest request) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            bicycle.setName( request.getName() );
        }
        if ( request.getBrand() != null ) {
            bicycle.setBrand( request.getBrand() );
        }
        if ( request.getType() != null ) {
            bicycle.setType( request.getType() );
        }
        if ( request.getPrice() != null ) {
            bicycle.setPrice( request.getPrice() );
        }
        if ( request.getStock() != null ) {
            bicycle.setStock( request.getStock() );
        }
        if ( request.getDescription() != null ) {
            bicycle.setDescription( request.getDescription() );
        }
        if ( request.getImageUrl() != null ) {
            bicycle.setImageUrl( request.getImageUrl() );
        }
    }

    @Override
    public BicycleSearchDTO toBicycleSearchDTO(Bicycle bicycle) {
        if ( bicycle == null ) {
            return null;
        }

        BicycleSearchDTO bicycleSearchDTO = new BicycleSearchDTO();

        bicycleSearchDTO.setId( bicycle.getId() );
        bicycleSearchDTO.setName( bicycle.getName() );
        bicycleSearchDTO.setBrand( bicycle.getBrand() );
        bicycleSearchDTO.setType( bicycle.getType() );
        bicycleSearchDTO.setPrice( bicycle.getPrice() );
        bicycleSearchDTO.setStock( bicycle.getStock() );
        bicycleSearchDTO.setDescription( bicycle.getDescription() );
        bicycleSearchDTO.setImageUrl( bicycle.getImageUrl() );
        bicycleSearchDTO.setCreatedAt( bicycle.getCreatedAt() );
        bicycleSearchDTO.setDeleted( bicycle.isDeleted() );

        return bicycleSearchDTO;
    }
}
