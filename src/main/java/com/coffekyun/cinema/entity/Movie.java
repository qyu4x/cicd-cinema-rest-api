package com.coffekyun.cinema.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE films SET deleted = TRUE WHERE id = ?")
@Table(name = "films")
@Where(clause = "deleted=false")

public class Movie {

    @Id
    private String id;

    @OneToMany(mappedBy = "movie")
    private List<Studio> studios;

    @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER)
    private List<Schedule> schedules;

    private String title;

    @Column(name = "show_status", columnDefinition = "boolean default true")
    private Boolean showStatus;

    private Integer duration;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(length = 512)
    private String description;

    private String genre;

    @Column(length = 25)
    private String country;

    private String language;

    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;

}
