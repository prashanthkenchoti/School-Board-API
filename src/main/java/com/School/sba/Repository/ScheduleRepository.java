package com.School.sba.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.School.sba.entity.Schedule;


public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

}
