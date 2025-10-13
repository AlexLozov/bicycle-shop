package com.BicycleShop.controller;

import com.BicycleShop.model.constants.ApiLogMessage;
import com.BicycleShop.model.dto.user.UserDTO;
import com.BicycleShop.model.request.user.NewUserRequest;
import com.BicycleShop.model.response.IamResponse;
import com.BicycleShop.service.UserService;
import com.BicycleShop.utils.ApiUtils;
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
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<IamResponse<UserDTO>> getUserById(@PathVariable("id") Integer id) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<UserDTO> response = userService.getById(id);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/create")
    public ResponseEntity<IamResponse<UserDTO>> createUser(
            @RequestBody @Valid NewUserRequest newUserRequest) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<UserDTO> response = userService.createUser(newUserRequest);
        return ResponseEntity.ok(response);
    }

}
