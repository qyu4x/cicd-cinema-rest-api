package com.coffekyun.cinema.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PasswordEncoderConfigurationTest {

    @Autowired
    private PasswordEncoderConfiguration passwordEncoderConfiguration;

    @Test
    void testEncode() {
        String passwordNotEncode1 = passwordEncoderConfiguration.passwordEncoder().encode("rahasia");
        String passwordNotEncode2 = passwordEncoderConfiguration.passwordEncoder().encode("rahasia");

        passwordEncoderConfiguration.passwordEncoder().matches("rahasia", passwordNotEncode1);
        passwordEncoderConfiguration.passwordEncoder().matches("rahasia", passwordNotEncode2);
    }
}