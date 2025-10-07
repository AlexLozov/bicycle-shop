package com.BicycleShop.service;

import com.BicycleShop.model.dto.bicycle.BicycleDTO;
import com.BicycleShop.model.request.bicycle.BicycleRequest;
import com.BicycleShop.model.response.IamResponse;
import jakarta.validation.constraints.NotNull;

public interface BicycleService {

    IamResponse<BicycleDTO> getById(@NotNull Integer id);
    IamResponse<BicycleDTO> createBicycle(@NotNull BicycleRequest bicycleRequest);
}
