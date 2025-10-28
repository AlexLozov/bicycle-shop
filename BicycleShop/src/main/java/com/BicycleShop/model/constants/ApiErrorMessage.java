package com.BicycleShop.model.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ApiErrorMessage {
    BICYCLE_WITH_ID_NOT_FOUND("Bicycle with ID: %s not found"),
    BICYCLE_WITH_NAME_ALREADY_EXISTS("Bicycle with name: '%s' already exists"),
    BICYCLE_STOCK_NOT_ENOUGH("Bicycle with stock: %d not enough"),

    USER_WITH_ID_NOT_FOUND("User with ID: %s not found"),
    USER_WITH_NAME_ALREADY_EXISTS("User with name: '%s' already exists"),
    USER_WITH_EMAIL_ALREADY_EXISTS("User with email: '%s' already exists"),
    USER_WITH_EMAIL_NOT_FOUND("User with email: '%s' not found"),
    USER_WITH_USERNAME_NOT_FOUND("User with username: '%s' not found"),

    CART_WITH_USER_ID_NOT_FOUND("Cart with user ID: %s not found"),
    CART_WITH_ID_NOT_FOUND("Cart with ID: %s not found"),

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


    MISMATCH_PASSWORDS("Password does not match"),
    INVALID_PASSWORD("Invalid password. It must have: "
            + "length at least " + ApiConstants.REQUIRED_MIN_PASSWORD_LENGTH + ", including "
            + ApiConstants.REQUIRED_MIN_LETTERS_NUMBER_EVERY_CASE_IN_PASSWORD + " letter(s) in upper and lower cases, "
            + ApiConstants.REQUIRED_MIN_CHARACTERS_NUMBER_IN_PASSWORD + " character(s), "
            + ApiConstants.REQUIRED_MIN_DIGITS_NUMBER_IN_PASSWORD + " digit(s). "),

    HAVE_NOT_ACCESS("You don't have the necessary permission"),


    ;

    private String message;

    public String getMessage(Object... args) {
        return String.format(message, args);
    }

}
