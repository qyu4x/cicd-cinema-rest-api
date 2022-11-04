package com.coffekyun.cinema.repository;

import com.coffekyun.cinema.entity.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudioRepository extends JpaRepository<Studio, String> {

    @Query(
            value = "SELECT * FROM studios WHERE films_id = ?1",
            nativeQuery = true
    )
    List<Studio> findByStudioId(String id);

}
