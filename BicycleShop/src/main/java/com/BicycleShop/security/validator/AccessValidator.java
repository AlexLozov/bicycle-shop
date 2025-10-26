package com.BicycleShop.security.validator;

import com.BicycleShop.model.constants.ApiErrorMessage;
import com.BicycleShop.model.entities.Role;
import com.BicycleShop.model.entities.ShoppingCart;
import com.BicycleShop.model.entities.User;
import com.BicycleShop.model.exception.DataExistException;
import com.BicycleShop.model.exception.InvalidDataException;
import com.BicycleShop.model.exception.InvalidPasswordException;
import com.BicycleShop.model.exception.NotFoundException;
import com.BicycleShop.repositories.ShoppingCartRepository;
import com.BicycleShop.repositories.UserRepository;
import com.BicycleShop.service.model.IamServiceUserRole;
import com.BicycleShop.utils.ApiUtils;
import com.BicycleShop.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AccessValidator {

    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public void validateNewUser(String username, String email, String password, String confirmPassword) {
        userRepository.findByUsername(username).ifPresent(existingUser -> {
            throw new DataExistException(ApiErrorMessage.USER_WITH_NAME_ALREADY_EXISTS.getMessage(username));
        });

        userRepository.findUserByEmail(email).ifPresent(existingUser -> {
            throw new DataExistException(ApiErrorMessage.USER_WITH_EMAIL_ALREADY_EXISTS.getMessage(email));
        });


        if (!password.equals(confirmPassword)) {
            throw new InvalidDataException(ApiErrorMessage.MISMATCH_PASSWORDS.getMessage());
        }

        if (PasswordUtils.isNotValidPassword(password)) {
            throw new InvalidPasswordException(ApiErrorMessage.INVALID_PASSWORD.getMessage());
        }
    }


    public boolean isAdminOrSuperAdmin(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_WITH_USERNAME_NOT_FOUND.getMessage(username)));

        return user.getRoles().stream()
                .map(role -> IamServiceUserRole.fromName(role.getName()))
                .anyMatch(role -> role == IamServiceUserRole.ADMIN || role == IamServiceUserRole.SUPER_ADMIN);
    }

    @SneakyThrows
    public void validateAdminOrOwnerAccess(String ownerUsername) {
        String currentUsername = ApiUtils.getCurrentUsername();

        if(!currentUsername.equals(ownerUsername) &&
                !isAdminOrSuperAdmin(currentUsername)) {
            throw new AccessDeniedException(ApiErrorMessage.HAVE_NOT_ACCESS.getMessage());
        }
    }



}
