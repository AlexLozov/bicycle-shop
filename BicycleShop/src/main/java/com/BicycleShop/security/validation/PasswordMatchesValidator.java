package com.BicycleShop.security.validation;

import com.BicycleShop.model.request.user.RegistrationUserRequest;
import com.BicycleShop.utils.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegistrationUserRequest> {
    @Override
    public boolean isValid(RegistrationUserRequest request, ConstraintValidatorContext constraintValidatorContext) {
        return request.getPassword().equals(request.getConfirmPassword());
    }
}
