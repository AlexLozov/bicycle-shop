package com.BicycleShop.service;

import com.BicycleShop.model.dto.bicycle.BicycleDTO;
import com.BicycleShop.model.dto.bicycle.BicycleSearchDTO;
import com.BicycleShop.model.request.bicycle.BicycleSearchRequest;
import com.BicycleShop.model.request.bicycle.NewBicycleRequest;
import com.BicycleShop.model.request.bicycle.UpdateBicycleRequest;
import com.BicycleShop.model.response.IamResponse;
import com.BicycleShop.model.response.PaginationResponse;
import jakarta.validation.constraints.NotNull;
import org.hibernate.sql.Update;
import org.springframework.data.domain.Pageable;

public interface BicycleService {

    IamResponse<BicycleDTO> getById(@NotNull Integer id);
    IamResponse<BicycleDTO> createBicycle(@NotNull NewBicycleRequest newBicycleRequest);
    IamResponse<BicycleDTO> updateBicycle(@NotNull Integer id, @NotNull UpdateBicycleRequest updateBicycleRequest);
    void softDeleteBicycle(@NotNull Integer id);

    IamResponse<PaginationResponse<BicycleSearchDTO>> findAllBicycles(Pageable pageable);
    IamResponse<PaginationResponse<BicycleSearchDTO>> searchBicycles(@NotNull BicycleSearchRequest request, Pageable pageable);
}
