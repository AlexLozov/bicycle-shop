package com.BicycleShop.controller;

import com.BicycleShop.model.constants.ApiLogMessage;
import com.BicycleShop.model.dto.shopping_cart.ShoppingCartDTO;
import com.BicycleShop.model.request.shopping_cart.AddToShoppingCart;
import com.BicycleShop.model.response.IamResponse;
import com.BicycleShop.service.ShoppingCartService;
import com.BicycleShop.utils.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping("/{userId}")
    @Operation(
            summary = "Получение корзины",
            description = "Получение корзины конкретного пользователя по его ID"
    )
    public ResponseEntity<IamResponse<ShoppingCartDTO>> getCartByUserId(
            @PathVariable(name = "userId") Integer userId){

        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<ShoppingCartDTO> response = shoppingCartService.getCartByUserId(userId);
        return ResponseEntity.ok(response);
    }



    @PostMapping("/add")
    @Operation(
            summary = "Добавление товара в корзину",
            description = "Добавление товара в корзину конкретного пользователя"
    )
    public ResponseEntity<IamResponse<ShoppingCartDTO>> addToCart(
            @Valid @RequestBody AddToShoppingCart request) {

        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<ShoppingCartDTO> response = shoppingCartService.addToCart(request);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{userId}")
    @Operation(
            summary = "Очистить корзину",
            description = "очистка корзины конкретного пользователя по его ID"
    )
    public ResponseEntity<Void> deleteCartByUserId(
            @PathVariable(name = "userId") Integer userId){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        shoppingCartService.clearCartByUserId(userId);
        return ResponseEntity.noContent().build();
    }

}
