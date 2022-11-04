package com.coffekyun.cinema.repository;

import com.coffekyun.cinema.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


public interface ScheduleRepository extends JpaRepository<Schedule, String> {

}
