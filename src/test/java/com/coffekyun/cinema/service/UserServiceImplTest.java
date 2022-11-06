package com.coffekyun.cinema.service;

import com.coffekyun.cinema.config.PasswordEncoderConfiguration;
import com.coffekyun.cinema.entity.Role;
import com.coffekyun.cinema.entity.User;
import com.coffekyun.cinema.exception.DataAlreadyExistsException;
import com.coffekyun.cinema.exception.DataNotFoundException;
import com.coffekyun.cinema.exception.NotMatchException;
import com.coffekyun.cinema.model.dto.UserRequest;
import com.coffekyun.cinema.model.dto.UserResponse;
import com.coffekyun.cinema.model.dto.UserUpdateRequest;
import com.coffekyun.cinema.model.enums.UserRole;
import com.coffekyun.cinema.repository.RoleRepository;
import com.coffekyun.cinema.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoderConfiguration passwordEncoderConfiguration;


    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegisterNewUserSuccess() {

        UserRequest userRequest = UserRequest.builder()
                .name("kaguya chan")
                .email("kaguyachan@gmail.com")
                .phone("08223234512")
                .password("secret")
                .build();


        User account = User.builder()
                .email(null)
                .build();

        Role user = new Role(
                UUID.randomUUID().toString(),
                UserRole.USER
        );

        Role customer = new Role(
                UUID.randomUUID().toString(),
                UserRole.CUSTOMER
        );

        User dataUser = User.builder()
                .id("random-id")
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .phone(userRequest.getPhone())
                .password("secret")
                .roles(Set.of(user, customer))
                .build();

        Mockito.when(userRepository.findByEmail(userRequest.getEmail()))
                        .thenReturn(account);

        Mockito.when(roleRepository.findByRole(UserRole.USER))
                .thenReturn(Optional.of(user));

        Mockito.when(roleRepository.findByRole(UserRole.CUSTOMER))
                .thenReturn(Optional.of(customer));

        Mockito.when(passwordEncoderConfiguration.passwordEncoder())
                .thenReturn(new BCryptPasswordEncoder());

        Mockito.when(userRepository.save(any(User.class)))
                .thenReturn(dataUser);

        UserResponse userResponse = userService.registerNewUserAccount(userRequest);

        Assertions.assertNotNull(userResponse);
        Assertions.assertEquals(userRequest.getEmail(), userResponse.getEmail());
        Assertions.assertEquals(userRequest.getName(), userResponse.getName());
        Assertions.assertEquals(userRequest.getPhone(), userResponse.getPhone());

        Mockito.verify(userRepository).findByEmail(userRequest.getEmail());
        Mockito.verify(roleRepository).findByRole(UserRole.USER);
        Mockito.verify(roleRepository).findByRole(UserRole.CUSTOMER);
        Mockito.verify(userRepository).save(any(User.class));

    }

    @Test
    void testRegisterNewUserFailed() {
        UserRequest userRequest = UserRequest.builder()
                .name("kaguya chan")
                .email("kaguyachan@gmail.com")
                .phone("08223234512")
                .password("secret")
                .build();

        User account = User.builder()
                .email("kaguyachan@gmail.com")
                .build();

        Mockito.when(userRepository.findByEmail(userRequest.getEmail()))
                .thenReturn(account);

        Assertions.assertThrows(DataAlreadyExistsException.class, () -> {
            userService.registerNewUserAccount(userRequest);
        });

        Mockito.verify(userRepository).findByEmail(userRequest.getEmail());
    }

    @Test
    void testUpdateUserAccountSuccess() {

        UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
                .name("chino kaffu")
                .email("chinochan@gmail.com")
                .oldPassword("secret")
                .newPassword("secretdesu")
                .build();

        User userUpdate  = User.builder()
                .id("random-id")
                .name(userUpdateRequest.getName())
                .email(userUpdateRequest.getEmail())
                .phone(userUpdateRequest.getPhone())
                .build();


        User dataUser = User.builder()
                .id("random-id")
                .name("kaguya chan")
                .email("kaguyachan@gmail.com")
                .password("$2a$10$Gz3YM/ptIVAhOE5OedPvXeIsp0WDWYCVyAk75RKXde6ycAhU0FWNu")
                .build();

        Mockito.when(userRepository.findById("random-id"))
                .thenReturn(Optional.of(dataUser));

        Mockito.when(passwordEncoderConfiguration.passwordEncoder())
                .thenReturn(new BCryptPasswordEncoder());

        Mockito.when(userRepository.save(any(User.class)))
                .thenReturn(userUpdate);

        UserResponse userResponse = userService.updateUserAccount(userUpdateRequest, "random-id");

        Assertions.assertNotNull(userResponse);
        Assertions.assertEquals(userUpdateRequest.getPhone(), userResponse.getPhone());
        Assertions.assertEquals(userUpdateRequest.getEmail(), userResponse.getEmail());
        Assertions.assertEquals(userUpdateRequest.getName(), userResponse.getName());

        Mockito.verify(userRepository).findById(userUpdate.getId());
        Mockito.verify(userRepository).save(any(User.class));

    }

    @Test
    void testUpdateUserAccountFailedAccountNotFound() {
        UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
                .name("chino kaffu")
                .email("chinochan@gmail.com")
                .oldPassword("secret")
                .newPassword("secretdesu")
                .build();

        Mockito.when(userRepository.findById("not-found"))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(DataNotFoundException.class, () -> {
            userService.updateUserAccount(userUpdateRequest, "not-found");
        });

        Mockito.verify(userRepository).findById("not-found");
    }

    @Test
    void testUpdateUserFailedPasswordDoesNotMatch() {
        UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
                .name("chino kaffu")
                .email("chinochan@gmail.com")
                .oldPassword("secret")
                .newPassword("secretdesu")
                .build();

        User dataUser = User.builder()
                .id("random-id")
                .name("kaguya chan")
                .email("kaguyachan@gmail.com")
                .password("$2a$10$Gz3YM/ptIVAhOE5")
                .build();

        Mockito.when(userRepository.findById("random-id"))
                .thenReturn(Optional.of(dataUser));

        Mockito.when(passwordEncoderConfiguration.passwordEncoder())
                .thenReturn(new BCryptPasswordEncoder());

        Assertions.assertThrows(NotMatchException.class, () -> {
            userService.updateUserAccount(userUpdateRequest, "random-id");
        });

        Mockito.verify(userRepository).findById("random-id");

    }

    @Test
    void testDeleteUserAccountSuccess() {
        Mockito.when(userRepository.existsById("id"))
                .thenReturn(true);

        userService.deleteUserAccount("id");

        Mockito.verify(userRepository).existsById("id");
        Mockito.verify(userRepository).deleteById("id");

    }

    @Test
    void testDeleteUserAccountFailed() {
        Mockito.when(userRepository.existsById("notfound"))
                .thenReturn(false);

       Assertions.assertThrows(DataNotFoundException.class, () -> {
           userService.deleteUserAccount("notfound");
       });

        Mockito.verify(userRepository).existsById("notfound");

    }

    @Test
    void testFindUserByIdSuccess() {
        User dataUser = User.builder()
                .email("kaguyachan@gmail.com")
                .name("kaguya chan")
                .phone("0983781739")
                .build();

        Mockito.when(userRepository.findById("user-id"))
                .thenReturn(Optional.of(dataUser));

        User user = userService.findById("user-id");

        Assertions.assertNotNull(user);
        Assertions.assertEquals(user.getName(), dataUser.getName());
        Assertions.assertEquals(user.getEmail(), dataUser.getEmail());
        Assertions.assertEquals(user.getPhone(), dataUser.getPhone());

        Mockito.verify(userRepository).findById("user-id");

    }

    @Test
    void testFindUserByIdFailed() {
        Mockito.when(userRepository.findById("not-found"))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(DataNotFoundException.class, () -> {
            userService.findById("not-found");
        });

        Mockito.verify(userRepository).findById("not-found");
    }
}