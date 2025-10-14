package com.BicycleShop.model.entities;

import com.BicycleShop.model.enums.RegistrationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 30)
    @Column(length = 30, nullable = false, unique = true)
    private String username;

    @Size(max = 80)
    @Column(length = 80, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDateTime created = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updated = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime last_login;

    @Column(nullable = false)
    private Boolean deleted = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "registration_status")
    private RegistrationStatus registrationStatus;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private ShoppingCart shoppingCart;


}
