package com.BicycleShop.controller;

import com.BicycleShop.model.constants.ApiLogMessage;
import com.BicycleShop.model.request.user.LoginRequest;
import com.BicycleShop.model.dto.user.UserProfileDTO;
import com.BicycleShop.model.request.user.RegistrationUserRequest;
import com.BicycleShop.model.response.IamResponse;
import com.BicycleShop.service.AuthService;
import com.BicycleShop.utils.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(
            summary = "Авторизация пользователя",
            description = "Требуется ввести email и пароль, в ответе выдается его JWT-token и Refresh-token"
    )
    public ResponseEntity<?> login(
            @RequestBody @Valid LoginRequest request,
            HttpServletResponse response) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<UserProfileDTO> result = authService.loginUser(request);
        Cookie authCookie = ApiUtils.createAuthCookie(result.getPayload().getToken());
        response.addCookie(authCookie);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/refresh/token")
    @Operation(
            summary = "Refresh token",
            description = "Требуется ввести refresh token пользователя, в ответе получаешь пользователя"
    )
    public ResponseEntity<IamResponse<UserProfileDTO>> refreshToken(
            @RequestParam(name="token") String refreshToken,
            HttpServletResponse response){
    log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

    IamResponse<UserProfileDTO> result = authService.refreshAccessToken(refreshToken);
    Cookie authCookie = ApiUtils.createAuthCookie(result.getPayload().getToken());
    response.addCookie(authCookie);

    return ResponseEntity.ok(result);
    }


        @PostMapping("/register")
        @Operation(
                summary = "Регистрация пользователя",
                description = "Требуется ввести email, username, password, confirm-password, в ответе выдается пользователь со всеми данными и JWT-token с Refresh-token"
        )
        public ResponseEntity<?> register(
                @RequestBody @Valid RegistrationUserRequest request,
                HttpServletResponse response){
            log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

            IamResponse<UserProfileDTO> result = authService.registerUser(request);
            Cookie authCookie = ApiUtils.createAuthCookie(result.getPayload().getToken());
            response.addCookie(authCookie);

            return ResponseEntity.ok(result);
        }

}
