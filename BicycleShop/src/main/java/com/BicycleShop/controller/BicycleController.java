package com.BicycleShop.controller;

import com.BicycleShop.model.constants.ApiErrorMessage;
import com.BicycleShop.model.constants.ApiLogMessage;
import com.BicycleShop.model.entities.Bicycle;
import com.BicycleShop.repositories.BicycleRepository;
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

    private final BicycleRepository bicycleRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Bicycle> getBicycleById(@PathVariable(name = "id") Integer id) {
        log.info(ApiLogMessage.BICYCLE_INFO_BY_ID.getMessage(id));

        return bicycleRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.info(ApiErrorMessage.BICYCLE_WITH_ID_NOT_FOUND.getMessage(id));
                    return ResponseEntity.notFound().build();
                });

    }


}
