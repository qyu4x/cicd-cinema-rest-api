package com.coffekyun.cinema.service;

import com.coffekyun.cinema.entity.User;
import com.coffekyun.cinema.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

    private final static Logger log = LoggerFactory.getLogger(JwtService.class);

    @Autowired
    private UserRepository userRepository;


    public Set<SimpleGrantedAuthority> getAuthorities(User user) {
        log.info("Generate roles by becoming set of simple granted authorities");
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        user.getRoles().forEach(
                role -> {
                    simpleGrantedAuthorities.add(
                            new SimpleGrantedAuthority("ROLE_" + role.getRole().toString())
                    );
                }
        );

        return simpleGrantedAuthorities;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("search user by email with user email {} ", username);
        User user = userRepository.findByEmail(username);
        if (user == null) {
            log.warn("user account is not found");
            throw new UsernameNotFoundException("user account is not found");
        }
        log.info("successful in getting users with email {} ", username);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                getAuthorities(user)
        );
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
