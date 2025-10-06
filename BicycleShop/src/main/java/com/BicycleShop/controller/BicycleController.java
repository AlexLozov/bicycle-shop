package com.BicycleShop.controller;

import com.BicycleShop.model.constants.ApiErrorMessage;
import com.BicycleShop.model.constants.ApiLogMessage;
import com.BicycleShop.model.dto.bicycle.BicycleDTO;
import com.BicycleShop.model.entities.Bicycle;
import com.BicycleShop.model.response.IamResponse;
import com.BicycleShop.repositories.BicycleRepository;
import com.BicycleShop.service.BicycleService;
import com.BicycleShop.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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


}
