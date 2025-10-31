package com.BicycleShop.controller;

import com.BicycleShop.model.constants.ApiLogMessage;
import com.BicycleShop.model.dto.bicycle.BicycleSearchDTO;
import com.BicycleShop.model.dto.user.FullUserDTO;
import com.BicycleShop.model.dto.user.UserDTO;
import com.BicycleShop.model.dto.user.UserSearchDTO;
import com.BicycleShop.model.request.bicycle.BicycleSearchRequest;
import com.BicycleShop.model.request.user.NewUserRequest;
import com.BicycleShop.model.request.user.UserSearchRequest;
import com.BicycleShop.model.response.IamResponse;
import com.BicycleShop.model.response.PaginationResponse;
import com.BicycleShop.service.UserService;
import com.BicycleShop.utils.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/users")
public class UserController {
    private final UserService userService;


    @GetMapping("/full/{id}")
    @Operation(
            summary = "Получить полного пользователя",
            description = "Получение полного пользователя + корзина - не рекомендуется использовать"
    )
    public ResponseEntity<IamResponse<FullUserDTO>> getFullUserById(@PathVariable("id") Integer id) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<FullUserDTO> response = userService.getFullUserById(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Просмотреть пользователя",
            description = "Просмотреть конкретного пользователя по его ID"
    )
    public ResponseEntity<IamResponse<UserDTO>> getUserById(
            @PathVariable(name = "id") Integer id){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<UserDTO> response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/create")
    @Operation(
            summary = "Создание пользователя",
            description = "Создание пользователя"
    )
    public ResponseEntity<IamResponse<UserDTO>> createUser(
            @RequestBody @Valid NewUserRequest newUserRequest) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<UserDTO> response = userService.createUser(newUserRequest);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление пользователя",
            description = "Теневое удаление пользователя"
    )
    public ResponseEntity<Void> softDeleteUserById(
            @PathVariable(name = "id") Integer id){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        userService.softDeleteUserById(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    @Operation(
            summary = "Изменение пользователя",
            description = "Частичное или полное изменение пользователя"
    )
    public ResponseEntity<IamResponse<UserDTO>> updateUserById(
            @PathVariable(name = "id") Integer id,
            @RequestBody @Valid NewUserRequest request){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<UserDTO> response = userService.updateUserById(id, request);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/all")
    @Operation(
            summary = "Список пользователей",
            description = "Список всех пользователей с учетом пагинации"
    )
    public ResponseEntity<IamResponse<PaginationResponse<UserSearchDTO>>> getAllUsers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "3") int limit) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        Pageable pageable = PageRequest.of(page, limit);
        IamResponse<PaginationResponse<UserSearchDTO>> response = userService.findAllUsers(pageable);

        return ResponseEntity.ok(response);
    }



    @PostMapping("/search")
    @Operation(
            summary = "Поиск пользователей",
            description = "Поиск пользователей по фильтру ,ответ с пагинацией"
    )
    public ResponseEntity<IamResponse<PaginationResponse<UserSearchDTO>>> searchUsers(
            @RequestBody @Valid UserSearchRequest request,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "3") int limit
    ) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        Pageable pageable = PageRequest.of(page, limit);
        IamResponse<PaginationResponse<UserSearchDTO>> response = userService.searchUsers(request, pageable);

        return ResponseEntity.ok(response);
    }


}
