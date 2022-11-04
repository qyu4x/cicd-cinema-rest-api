package com.coffekyun.cinema.service;

import com.coffekyun.cinema.config.PasswordEncoderConfiguration;
import com.coffekyun.cinema.entity.Role;
import com.coffekyun.cinema.entity.User;
import com.coffekyun.cinema.model.enums.UserRole;
import com.coffekyun.cinema.model.request.RoleRequest;
import com.coffekyun.cinema.model.response.RoleResponse;
import com.coffekyun.cinema.repository.RoleRepository;
import com.coffekyun.cinema.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Service
public class RoleServiceImpl implements RoleService{

    private final static Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;
    private final PasswordEncoderConfiguration passwordEncoderConfiguration;
    private final UserRepository userRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, PasswordEncoderConfiguration passwordEncoderConfiguration, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoderConfiguration = passwordEncoderConfiguration;
        this.userRepository = userRepository;
    }

    @Override
    public RoleResponse save(RoleRequest roleRequest) {
        Role role = new Role(
                UUID.randomUUID().toString(),
                roleRequest.getRole()
        );

        roleRepository.save(role);

        return new RoleResponse(
                role.getId(),
                role.getRole()
        );
    }

    @Override
    public void initRoleUserAndAdmin() {

        Role isUserRoleExist = roleRepository.findByRole(UserRole.USER)
                .orElse(null);

        Role isAdminRoleExist = roleRepository.findByRole(UserRole.ADMIN)
                .orElse(null);

        Role isCustomerRoleExist = roleRepository.findByRole(UserRole.CUSTOMER)
                .orElse(null);

        if (isAdminRoleExist == null && isUserRoleExist == null && isCustomerRoleExist == null) {
            Role adminRole = new Role(
                    UUID.randomUUID().toString(),
                    UserRole.ADMIN
            );

            Role userRole = new Role(
                    UUID.randomUUID().toString(),
                    UserRole.USER
            );

            Role customerRole = new Role(
                    UUID.randomUUID().toString(),
                    UserRole.CUSTOMER
            );

            roleRepository.save(adminRole);
            roleRepository.save(userRole);
            roleRepository.save(customerRole);

            Set<Role> managerRoles = new HashSet<>();
            managerRoles.add(adminRole);
            managerRoles.add(userRole);
            managerRoles.add(customerRole);

            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);

            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);

            User manager = new User(
                    UUID.randomUUID().toString(),
                    "ai",
                    "aichan@gmail.com",
                    "0822671281271",
                    passwordEncoderConfiguration.passwordEncoder().encode("aichan"),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    null,
                    false,
                    managerRoles
            );
            userRepository.save(manager);

            User admin = new User(
                    UUID.randomUUID().toString(),
                    "chinochan",
                    "chinochan@gmail.com",
                    "0822671281271",
                    passwordEncoderConfiguration.passwordEncoder().encode("chinochan"),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    null,
                    false,
                    adminRoles
            );

            userRepository.save(admin);

            // save init admin
            User user = new User(
                    UUID.randomUUID().toString(),
                    "kaguyachan",
                    "kaguya@gmail.com",
                    "0822671281271",
                    passwordEncoderConfiguration.passwordEncoder().encode("kaguyachan"),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    null,
                    false,
                    userRoles
            );

            userRepository.save(user);
        } else {
            log.warn("init role user and admin is available");
        }

    }
}
