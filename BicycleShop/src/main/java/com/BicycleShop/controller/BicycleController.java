package com.BicycleShop.controller;

import com.BicycleShop.model.constants.ApiLogMessage;
import com.BicycleShop.model.dto.bicycle.BicycleDTO;
import com.BicycleShop.model.dto.bicycle.BicycleSearchDTO;
import com.BicycleShop.model.request.bicycle.BicycleSearchRequest;
import com.BicycleShop.model.request.bicycle.NewBicycleRequest;
import com.BicycleShop.model.request.bicycle.UpdateBicycleRequest;
import com.BicycleShop.model.response.IamResponse;
import com.BicycleShop.model.response.PaginationResponse;
import com.BicycleShop.service.BicycleService;
import com.BicycleShop.utils.ApiUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/bicycle")
public class BicycleController {

    private final BicycleService bicycleService;

    @GetMapping("/{id}")
    public ResponseEntity<IamResponse<BicycleDTO>> getBicycleById(@PathVariable(name = "id") Integer id) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<BicycleDTO> response = bicycleService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<IamResponse<BicycleDTO>> createBicycle(
            @RequestBody @Valid NewBicycleRequest newBicycleRequest) {

        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());
        IamResponse<BicycleDTO> response = bicycleService.createBicycle(newBicycleRequest);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<IamResponse<BicycleDTO>> updateBicycle(
            @PathVariable(name="id") Integer id,
                    @RequestBody @Valid UpdateBicycleRequest request){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<BicycleDTO> response = bicycleService.updateBicycle(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteBicycleById(
            @PathVariable(name = "id") Integer id) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        bicycleService.softDeleteBicycle(id);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/all")
    public ResponseEntity<IamResponse<PaginationResponse<BicycleSearchDTO>>> getAllBicycles(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "3") int limit
    ) {

        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        Pageable pageable = PageRequest.of(page, limit);
        IamResponse<PaginationResponse<BicycleSearchDTO>> response = bicycleService.findAllBicycles(pageable);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/search")
    public ResponseEntity<IamResponse<PaginationResponse<BicycleSearchDTO>>> searchBicycles(
            @RequestBody @Valid BicycleSearchRequest request,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "3") int limit
    ){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        Pageable pageable = PageRequest.of(page, limit);
        IamResponse<PaginationResponse<BicycleSearchDTO>> response = bicycleService.searchBicycles(request, pageable);

        return ResponseEntity.ok(response);
    }


}