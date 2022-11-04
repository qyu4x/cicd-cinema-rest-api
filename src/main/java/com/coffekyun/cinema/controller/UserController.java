package com.coffekyun.cinema.controller;

import com.coffekyun.cinema.model.dto.GlobalResponseHandler;
import com.coffekyun.cinema.model.dto.UserRequest;
import com.coffekyun.cinema.model.dto.UserResponse;
import com.coffekyun.cinema.model.dto.UserUpdateRequest;
import com.coffekyun.cinema.exception.DataAlreadyExistsException;
import com.coffekyun.cinema.exception.DataNotFoundException;
import com.coffekyun.cinema.exception.GlobalExceptionHandler;
import com.coffekyun.cinema.exception.NotMatchException;
import com.coffekyun.cinema.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user/")
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "register a user account"
    )
    @PostMapping(value = "/sign-up")
    @ResponseBody
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> postRequestRegister(@Valid @RequestBody UserRequest userRequest) {
        log.info("#calling controller postRequestRegister");
        try {
            UserResponse userResponse = userService.registerNewUserAccount(userRequest);
            log.info("#successfully register user with email {} ", userResponse.getEmail());
            return GlobalResponseHandler
                    .generateResponse("successfully register user with id "  + userResponse.getId() , HttpStatus.OK, userResponse);
        }catch (DataAlreadyExistsException exception) {
            log.info("#failed register user with email {} ", userRequest.getEmail());
            return GlobalExceptionHandler.dataAlreadyExistsHandler(exception.getMessage());
        }
    }


    @Operation(
            summary = "update user account based on the user id"
    )
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PutMapping(value = "/update/{id}")
    @ResponseBody
    public ResponseEntity<?> postRequestUpdate(@Valid @RequestBody UserUpdateRequest userUpdateRequest , @PathVariable String id) {
        log.info("#calling controller postRequestUpdate");
        try {
            UserResponse userResponse = userService.updateUserAccount(userUpdateRequest, id);
            log.info("#successfully update user with id {} ", id);
            return GlobalResponseHandler
                    .generateResponse("successfully update user with id "  + userResponse.getId() , HttpStatus.OK, userResponse);
        }catch (DataNotFoundException | NotMatchException exception) {
            log.info("#failed update user with id {}, error {} ", id, exception.getMessage());
            return GlobalExceptionHandler.dataNotFoundHandler(exception.getMessage());
        }
    }


    @Operation(
            summary = "delete user account based on the user id"
    )
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @DeleteMapping(value = "/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> requestDeleteUserAccount(@PathVariable("id") String id) {
        log.info("#calling controller requestDeleteUserAccount");
        try {
            userService.deleteUserAccount(id);
            log.info("#successfully delete user with id {}", id);
            return GlobalResponseHandler
                    .generateResponse("successfully delete data with id "  + id ,HttpStatus.OK, null);
        }catch (DataNotFoundException exception) {
            log.info("#failed delete user with id {}", id);
            return GlobalExceptionHandler.dataNotFoundHandler(exception.getMessage());
        }
    }


}
