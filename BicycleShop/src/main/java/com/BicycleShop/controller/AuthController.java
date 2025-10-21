package com.BicycleShop.controller;

import com.BicycleShop.model.constants.ApiLogMessage;
import com.BicycleShop.model.dto.user.LoginRequest;
import com.BicycleShop.model.dto.user.UserProfileDTO;
import com.BicycleShop.model.response.IamResponse;
import com.BicycleShop.service.AuthService;
import com.BicycleShop.utils.ApiUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
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
    public ResponseEntity<IamResponse<UserProfileDTO>> refreshToken(
            @RequestParam(name="token") String refreshToken,
            HttpServletResponse response){
    log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

    IamResponse<UserProfileDTO> result = authService.refreshAccessToken(refreshToken);
    Cookie authCookie = ApiUtils.createAuthCookie(result.getPayload().getToken());
    response.addCookie(authCookie);

    return ResponseEntity.ok(result);

    }

}
