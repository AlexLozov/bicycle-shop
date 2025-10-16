package com.BicycleShop.model.request.user;

import com.BicycleShop.model.enums.UserSortField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchRequest implements Serializable {

    private String username;
    private String email;

    private Boolean deleted;
    private String keyword;
    private UserSortField sortField;
}
