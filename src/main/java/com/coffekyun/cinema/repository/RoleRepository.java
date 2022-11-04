package com.coffekyun.cinema.repository;

import com.coffekyun.cinema.entity.Role;
import com.coffekyun.cinema.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByRole(UserRole user);
}
