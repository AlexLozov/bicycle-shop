package com.BicycleShop.service.impl;

import com.BicycleShop.mapper.BicycleMapper;
import com.BicycleShop.model.constants.ApiErrorMessage;
import com.BicycleShop.model.dto.bicycle.BicycleDTO;
import com.BicycleShop.model.dto.bicycle.BicycleSearchDTO;
import com.BicycleShop.model.entities.Bicycle;
import com.BicycleShop.model.exception.DataExistException;
import com.BicycleShop.model.exception.NotFoundException;
import com.BicycleShop.model.request.bicycle.BicycleSearchRequest;
import com.BicycleShop.model.request.bicycle.NewBicycleRequest;
import com.BicycleShop.model.request.bicycle.UpdateBicycleRequest;
import com.BicycleShop.model.response.IamResponse;
import com.BicycleShop.model.response.PaginationResponse;
import com.BicycleShop.repositories.BicycleRepository;
import com.BicycleShop.repositories.criteria.BicycleSearchCriteria;
import com.BicycleShop.security.validator.AccessValidator;
import com.BicycleShop.service.BicycleService;
import com.BicycleShop.utils.ApiUtils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BicycleServiceImpl implements BicycleService {
    private final BicycleRepository bicycleRepository;
    private final BicycleMapper bicycleMapper;

    @Override
    public IamResponse<BicycleDTO> getById(@NotNull Integer id) {
        Bicycle bicycle = bicycleRepository.findByIdAndDeletedFalse(id)
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
        Bicycle bicycle = bicycleRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException((ApiErrorMessage.BICYCLE_WITH_ID_NOT_FOUND.getMessage(id))));

        bicycleMapper.updateBicycle(bicycle, request);
        bicycle.setUpdatedAt(LocalDateTime.now());
        bicycle = bicycleRepository.save(bicycle);

        BicycleDTO bicycleDTO = bicycleMapper.toBicycleDTO(bicycle);
        return IamResponse.createSuccessful(bicycleDTO);
    }


    @Override
    public void softDeleteBicycle(Integer id) {
        Bicycle bicycle = bicycleRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException((ApiErrorMessage.BICYCLE_WITH_ID_NOT_FOUND.getMessage(id))));

        bicycle.setDeleted(true);
        bicycleRepository.save(bicycle);
    }

    @Override
    public IamResponse<PaginationResponse<BicycleSearchDTO>> findAllBicycles(Pageable pageable) {
        Page<BicycleSearchDTO> bicycles = bicycleRepository.findAll(pageable)
                .map(bicycleMapper::toBicycleSearchDTO);

        PaginationResponse<BicycleSearchDTO> response = new PaginationResponse<>(
                bicycles.getContent(),
                new PaginationResponse.Pagination(
                        pageable.getPageSize(),
                        bicycles.getTotalPages(),
                        bicycles.getNumber() + 1,
                        bicycles.getTotalElements()

                )
        );
        return IamResponse.createSuccessful(response);
    }

    @Override
    public IamResponse<PaginationResponse<BicycleSearchDTO>> searchBicycles(
            @NotNull BicycleSearchRequest request,
            Pageable pageable) {

        Specification<Bicycle> specification = new BicycleSearchCriteria(request);

        Page<BicycleSearchDTO> bicycles = bicycleRepository.findAll(specification, pageable)
                .map(bicycleMapper::toBicycleSearchDTO);

        PaginationResponse<BicycleSearchDTO> response = PaginationResponse.<BicycleSearchDTO>builder()
                .content(bicycles.getContent())
                .pagination(PaginationResponse.Pagination.builder()
                        .total(bicycles.getTotalElements())
                        .limit(pageable.getPageSize())
                        .page(bicycles.getNumber() + 1)
                        .pages(bicycles.getTotalPages())
                        .build())
                .build();

        return IamResponse.createSuccessful(response);
    }
}
