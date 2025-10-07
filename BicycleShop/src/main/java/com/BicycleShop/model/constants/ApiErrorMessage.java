package com.BicycleShop.model.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ApiErrorMessage {
    BICYCLE_WITH_ID_NOT_FOUND("Bicycle with ID: %s not found"),
    BICYCLE_WITH_NAME_ALREADY_EXISTS("Bicycle with name: '%s' already exists"),
    ;

    private String message;

    public String getMessage(Object... args) {
        return String.format(message, args);
    }

}
