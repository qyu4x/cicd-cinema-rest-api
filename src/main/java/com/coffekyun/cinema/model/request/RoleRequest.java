package com.coffekyun.cinema.model.request;

import com.coffekyun.cinema.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleRequest {

    @NotEmpty(message = "The role is required.")
    private UserRole role;

}
