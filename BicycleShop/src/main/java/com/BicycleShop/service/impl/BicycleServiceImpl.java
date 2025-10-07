package com.BicycleShop.service.impl;

import com.BicycleShop.mapper.BicycleMapper;
import com.BicycleShop.model.constants.ApiErrorMessage;
import com.BicycleShop.model.dto.bicycle.BicycleDTO;
import com.BicycleShop.model.entities.Bicycle;
import com.BicycleShop.model.exception.DataExistException;
import com.BicycleShop.model.exception.NotFoundException;
import com.BicycleShop.model.request.bicycle.NewBicycleRequest;
import com.BicycleShop.model.request.bicycle.UpdateBicycleRequest;
import com.BicycleShop.model.response.IamResponse;
import com.BicycleShop.repositories.BicycleRepository;
import com.BicycleShop.service.BicycleService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    public IamResponse<BicycleDTO> createBicycle(@NotNull NewBicycleRequest newBicycleRequest) {
        if(bicycleRepository.existsByName(newBicycleRequest.getName())){
            throw new DataExistException(ApiErrorMessage.BICYCLE_WITH_NAME_ALREADY_EXISTS.getMessage(newBicycleRequest.getName()));
        }

        Bicycle bicycle = bicycleMapper.createBicycle(newBicycleRequest);
        Bicycle createdBicycle = bicycleRepository.save(bicycle);
        BicycleDTO bicycleDTO = bicycleMapper.toBicycleDTO(createdBicycle);

        return IamResponse.createSuccessful(bicycleDTO);
    }

    @Override
    public IamResponse<BicycleDTO> updateBicycle(@NotNull Integer id,@NotNull UpdateBicycleRequest request) {
        Bicycle bicycle = bicycleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException((ApiErrorMessage.BICYCLE_WITH_ID_NOT_FOUND.getMessage(id))));

        bicycleMapper.updateBicycle(bicycle, request);
        bicycle.setUpdatedAt(LocalDateTime.now());
        bicycle = bicycleRepository.save(bicycle);

        BicycleDTO bicycleDTO = bicycleMapper.toBicycleDTO(bicycle);
        return IamResponse.createSuccessful(bicycleDTO);
    }
}
