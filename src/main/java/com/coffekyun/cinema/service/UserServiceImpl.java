package com.coffekyun.cinema.service;

import com.coffekyun.cinema.config.PasswordEncoderConfiguration;
import com.coffekyun.cinema.entity.Role;
import com.coffekyun.cinema.model.dto.UserRequest;
import com.coffekyun.cinema.model.dto.UserResponse;
import com.coffekyun.cinema.model.dto.UserUpdateRequest;
import com.coffekyun.cinema.entity.User;
import com.coffekyun.cinema.exception.DataAlreadyExistsException;
import com.coffekyun.cinema.exception.DataNotFoundException;
import com.coffekyun.cinema.exception.NotMatchException;
import com.coffekyun.cinema.model.enums.UserRole;
import com.coffekyun.cinema.repository.RoleRepository;
import com.coffekyun.cinema.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService{

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoderConfiguration passwordEncoderConfiguration;
    @Override
    public UserResponse registerNewUserAccount(UserRequest userRequest) {
        log.info("do register new user account");
        if (userRepository.findByEmail(userRequest.getEmail()) != null) {
            log.warn("user with email {} is already exist", userRequest.getEmail());
            throw new DataAlreadyExistsException(
                    "There is an account with that email adress:" + userRequest.getEmail()
            );
        } else {
            Set<Role> userRoles = new HashSet<>();
            Role roleUser = roleRepository.findByRole(UserRole.USER).get();
            Role roleCustomer = roleRepository.findByRole(UserRole.CUSTOMER).get();
            userRoles.add(roleUser);
            userRoles.add(roleCustomer);

            User user = userRequest.toUser();
            user.setId(UUID.randomUUID().toString());
            user.setRoles(userRoles);
            user.setPassword(
                    passwordEncoderConfiguration.passwordEncoder().encode(user.getPassword())
            );
            User registeredUser = userRepository.save(user);
            log.info("successfuly register user with email {} ", userRequest.getEmail());
            return UserResponse.builder()
                    .id(registeredUser.getId())
                    .name(registeredUser.getName())
                    .email(registeredUser.getEmail())
                    .phone(registeredUser.getPhone())
                    .build();
        }
    }

    @Override
    public UserResponse updateUserAccount(UserUpdateRequest userRequest, String id) {
        log.info("do upadate user with user id {} ", id);
        User toUser = userRequest.toUser();
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            User updateUser = user.get();
            boolean match =  passwordEncoderConfiguration.passwordEncoder().matches(userRequest.getOldPassword(), updateUser.getPassword());
            if (match) {
                updateUser.setName(toUser.getName());
                updateUser.setEmail(toUser.getEmail());
                updateUser.setUpdatedAt(LocalDateTime.now());
                updateUser.setPassword(
                        passwordEncoderConfiguration.passwordEncoder().encode(userRequest.getNewPassword())
                );

                User updatedUser = userRepository.save(updateUser);
                log.info("successfuly update user with id {} ", id);
                return UserResponse.builder()
                        .id(updatedUser.getId())
                        .name(updatedUser.getName())
                        .email(updateUser.getEmail())
                        .phone(updateUser.getPhone())
                        .build();
            } else {
                log.error("passwords don't match for user id {}", id);
                throw new NotMatchException("Opps, passwords don't match");
            }
        } else {
            log.info("user with id {} not found", id);
            throw new DataNotFoundException("user with id " + id + " not found");
        }
    }

    @Override
    public void deleteUserAccount(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            log.info("successfully delet user with id {}", id);
        } else {
            log.info("user with id {} not found", id);
            throw new DataNotFoundException("user with id " + id + " not found");
        }
    }

    @Override
    public User findById(String id) {
        log.info("do find user by id {} ", id);
        Optional<User> userResposne = userRepository.findById(id);
        if (userResposne.isEmpty()) {
            log.warn("user with id {} not found", id);
            throw new DataNotFoundException("user with id " + id + " not found");
        } else {
            log.info("successfully get user with id {}", id);
            User user = userResposne.get();
            return User.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .phone(user.getPhone()).build();

        }
    }
}
