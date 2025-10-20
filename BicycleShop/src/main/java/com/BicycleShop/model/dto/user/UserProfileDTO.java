package com.BicycleShop.model.dto.user;

import com.BicycleShop.model.dto.role.RoleDTO;
import com.BicycleShop.model.enums.RegistrationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO implements Serializable {

    private Integer id;
    private String username;
    private String email;

    private RegistrationStatus registrationStatus;
    private LocalDateTime last_login;

    private String token;
    private List<RoleDTO> roles;


}
