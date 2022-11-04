package com.coffekyun.cinema.controller;

import com.coffekyun.cinema.model.request.RoleRequest;
import com.coffekyun.cinema.model.response.RoleResponse;
import com.coffekyun.cinema.model.response.WebResponse;
import com.coffekyun.cinema.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;



@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final static Logger log = LoggerFactory.getLogger(RoleController.class);

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostConstruct
    public void postInitRoleUserAndAdmin() {
        log.info("#calling postInitRoleUserAndAdmin");
        roleService.initRoleUserAndAdmin();
        log.info("#successfully entered the main user and admin data");

    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> save(@RequestBody RoleRequest roleRequest) {
        log.info("#calling method save role");
        WebResponse<RoleResponse> webResponse = new WebResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                roleService.save(roleRequest)
        );
        log.info("#successfully enter the role");

        return new ResponseEntity<>(webResponse, HttpStatus.OK);
    }

}
