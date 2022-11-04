package com.coffekyun.cinema.model.response;

import com.coffekyun.cinema.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleResponse {

    private String id;

    private UserRole role;

}
