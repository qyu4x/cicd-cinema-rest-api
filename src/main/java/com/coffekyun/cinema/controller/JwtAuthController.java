package com.coffekyun.cinema.controller;

import com.coffekyun.cinema.entity.User;
import com.coffekyun.cinema.model.enums.UserRole;
import com.coffekyun.cinema.model.request.JwtRequest;
import com.coffekyun.cinema.model.response.JwtResponse;
import com.coffekyun.cinema.model.response.RoleResponse;
import com.coffekyun.cinema.model.response.WebResponse;
import com.coffekyun.cinema.service.JwtService;
import com.coffekyun.cinema.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping(value = "/auth/")
public class JwtAuthController {

    private final static Logger log = LoggerFactory.getLogger(JwtAuthController.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public JwtAuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> createJwtToken(@RequestBody JwtRequest jwtRequest) {
        try {
            log.info("#calling login role");
            authenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
            log.info("#login succeess user account {} ", jwtRequest.getEmail());
            UserDetails userDetails = jwtService.loadUserByUsername(jwtRequest.getEmail());
            log.info("#load user {} details success", userDetails.getUsername());
            String jwtToken = jwtUtil.generateToken(userDetails);
            User user = jwtService.findByEmail(jwtRequest.getEmail());

            Set<UserRole> roleResponse = new HashSet<>();
            user.getRoles().forEach(role -> {
                roleResponse.add(role.getRole());
            });
            JwtResponse jwtResponse = new JwtResponse(
                    user.getEmail(),
                    user.getName(),
                    roleResponse,
                    jwtToken
            );

            log.info("success create token");
            WebResponse<JwtResponse> webResponse = new WebResponse(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    jwtResponse
            );
            return new ResponseEntity<>(webResponse, HttpStatus.OK);
        }catch (Exception exception) {
            log.error("error create login and create token for user account {}, error {}",jwtRequest.getEmail(), exception.getMessage());
            WebResponse<JwtResponse> webResponse = new WebResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    null
            );
            return new ResponseEntity<>(webResponse, HttpStatus.UNAUTHORIZED);
        }
    }

    public void authenticate(String email, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (DisabledException exception) {
            log.error("user account {} is disabled", email);
            throw new RuntimeException("account is disabled");
        } catch (BadCredentialsException exception) {
            log.error("username or password is wrong for user account {} ", email);
            throw new RuntimeException("email or password is wrong");
        }
    }


}
