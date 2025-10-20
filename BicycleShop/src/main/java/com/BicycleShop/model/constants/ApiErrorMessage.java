package com.BicycleShop.model.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ApiErrorMessage {
    BICYCLE_WITH_ID_NOT_FOUND("Bicycle with ID: %s not found"),
    BICYCLE_WITH_NAME_ALREADY_EXISTS("Bicycle with name: '%s' already exists"),

    USER_WITH_ID_NOT_FOUND("User with ID: %s not found"),
    USER_WITH_NAME_ALREADY_EXISTS("User with name: '%s' already exists"),
    USER_WITH_EMAIL_ALREADY_EXISTS("User with email: '%s' already exists"),
    USER_WITH_EMAIL_NOT_FOUND("User with email: '%s' not found"),

    CART_WITH_USER_ID_NOT_FOUND("Cart with user ID: %s not found"),

    ROLE_WITH_NAME_NOT_FOUND("Role with name: '%s' not found"),
//------------------------------------------------------------------------------------
    INVALID_TOKEN_SIGNATURE("Invalid token signature"),

    UNEXPECTED_ERROR("An unexpected error occurred. Please try again later."),


    ERROR_DURING_JWT_PROCESSING("An unexpected error occurred during JWT processing"),
    TOKEN_EXPIRED("Token expired."),
    UNEXPECTED_ERROR_OCCURRED("An unexpected error occurred. Please try again later."),

    AUTHENTICATION_FAILED_FOR_USER("Authentication failed for user: {}. "),
    INVALID_USER_OR_PASSWORD("Invalid email or password. Try again"),
    INVALID_USER_REGISTRATION_STATUS("Invalid user registration status: %s. "),
    NOT_FOUND_REFRESH_TOKEN("Refresh token not found."),
    ;

    private String message;

    public String getMessage(Object... args) {
        return String.format(message, args);
    }

}
