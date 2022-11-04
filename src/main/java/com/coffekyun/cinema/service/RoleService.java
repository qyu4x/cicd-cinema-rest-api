package com.coffekyun.cinema.service;

import com.coffekyun.cinema.model.request.RoleRequest;
import com.coffekyun.cinema.model.response.RoleResponse;

public interface RoleService {

    RoleResponse save(RoleRequest roleRequest);

    void initRoleUserAndAdmin();

}
