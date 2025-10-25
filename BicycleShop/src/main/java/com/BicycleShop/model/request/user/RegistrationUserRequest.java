package com.BicycleShop.model.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationUserRequest implements Serializable {
    @NotBlank
    private String username;
    @Email
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;

}
