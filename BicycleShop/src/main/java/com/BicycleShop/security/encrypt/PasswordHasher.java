package com.BicycleShop.security.encrypt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String first = encoder.encode("qwerty123");

        System.out.println(first);
    }
}
