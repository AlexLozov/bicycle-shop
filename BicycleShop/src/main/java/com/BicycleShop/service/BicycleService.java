package com.BicycleShop.service;

import com.BicycleShop.model.dto.bicycle.BicycleDTO;
import com.BicycleShop.model.request.bicycle.NewBicycleRequest;
import com.BicycleShop.model.request.bicycle.UpdateBicycleRequest;
import com.BicycleShop.model.response.IamResponse;
import jakarta.validation.constraints.NotNull;
import org.hibernate.sql.Update;

public interface BicycleService {

    IamResponse<BicycleDTO> getById(@NotNull Integer id);
    IamResponse<BicycleDTO> createBicycle(@NotNull NewBicycleRequest newBicycleRequest);
    IamResponse<BicycleDTO> updateBicycle(@NotNull Integer id, @NotNull UpdateBicycleRequest updateBicycleRequest);
}
