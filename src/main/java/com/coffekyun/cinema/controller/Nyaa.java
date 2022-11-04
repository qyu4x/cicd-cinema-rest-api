package com.coffekyun.cinema.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Nyaa {

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/nya")
    public String hello() {
        return "Nyaa";
    }


    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/desu")
    public String desu() {
        return "Nyaa desuu";
    }
}
