package com.coffekyun.cinema.repository;

import com.coffekyun.cinema.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, String> {

    Optional<Movie> findById(String id);

    @Modifying
    @Query(
            value = "UPDATE films SET title = ?1, updated_at = ?2 WHERE id = ?3",
            nativeQuery = true
    )
    Integer updateTitleById(String title, Date updatedAt, String id);

    @Query(
            value = "SELECT * FROM films as f WHERE show_status = ?1",
            nativeQuery = true
    )
    List<Movie> findAllIfLive(Boolean status);

    @Query(
            value = "SELECT * FROM films as f JOIN schedules as s ON f.id = s.films_id  WHERE f.id = ?1",
            nativeQuery = true
    )
    Movie findScheduleByIdMovie(String id);

}
