package com.coffekyun.cinema.model.response;

import com.coffekyun.cinema.entity.User;
import com.coffekyun.cinema.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtResponse {

    private String email;

    private String name;

    private Set<UserRole> roleResponses;

    private String jwtToken;

}
