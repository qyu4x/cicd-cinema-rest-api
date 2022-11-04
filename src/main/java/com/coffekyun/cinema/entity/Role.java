package com.coffekyun.cinema.entity;

import com.coffekyun.cinema.model.enums.UserRole;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private UserRole role;

}
