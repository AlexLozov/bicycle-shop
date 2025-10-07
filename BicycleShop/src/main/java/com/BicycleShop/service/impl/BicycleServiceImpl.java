package com.BicycleShop.service.impl;

import com.BicycleShop.mapper.BicycleMapper;
import com.BicycleShop.model.constants.ApiErrorMessage;
import com.BicycleShop.model.dto.bicycle.BicycleDTO;
import com.BicycleShop.model.entities.Bicycle;
import com.BicycleShop.model.exception.NotFoundException;
import com.BicycleShop.model.request.bicycle.BicycleRequest;
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
    private final BicycleMapper bicycleMapper;

    @Override
    public IamResponse<BicycleDTO> getById(@NotNull Integer id) {
        Bicycle bicycle = bicycleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException((ApiErrorMessage.BICYCLE_WITH_ID_NOT_FOUND.getMessage(id))));


        BicycleDTO bicycleDTO = bicycleMapper.toBicycleDTO(bicycle);

        return IamResponse.createSuccessful(bicycleDTO);
    }

    @Override
    public IamResponse<BicycleDTO> createBicycle(@NotNull BicycleRequest bicycleRequest) {
        Bicycle bicycle = bicycleMapper.createBicycle(bicycleRequest);
        Bicycle createdBicycle = bicycleRepository.save(bicycle);
        BicycleDTO bicycleDTO = bicycleMapper.toBicycleDTO(createdBicycle);

        return IamResponse.createSuccessful(bicycleDTO);
    }
}
