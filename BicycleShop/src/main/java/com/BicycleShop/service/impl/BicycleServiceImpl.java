package com.BicycleShop.service.impl;

import com.BicycleShop.model.constants.ApiErrorMessage;
import com.BicycleShop.model.dto.bicycle.BicycleDTO;
import com.BicycleShop.model.entities.Bicycle;
import com.BicycleShop.model.exception.NotFoundException;
import com.BicycleShop.model.response.IamResponse;
import com.BicycleShop.repositories.BicycleRepository;
import com.BicycleShop.service.BicycleService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BicycleServiceImpl implements BicycleService {
    private final BicycleRepository bicycleRepository;

    @Override
    public IamResponse<BicycleDTO> getById(@NotNull Integer id) {
        Bicycle bicycle = bicycleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException((ApiErrorMessage.BICYCLE_WITH_ID_NOT_FOUND.getMessage(id))));


        BicycleDTO bicycleDTO = BicycleDTO.builder()
                .id(bicycle.getId())
                .brand(bicycle.getBrand())
                .type(bicycle.getType())
                .stock(bicycle.getStock())
                .price(bicycle.getPrice())
                .createdAt(bicycle.getCreatedAt())
                .updatedAt(bicycle.getUpdatedAt())
                .description(bicycle.getDescription())
                .imageUrl(bicycle.getImageUrl())
                .build();

        return IamResponse.createSuccessful(bicycleDTO);
    }
}
