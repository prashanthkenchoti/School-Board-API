package com.School.sba.Repository;

import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.School.sba.entity.ClassHour;

@Repository
public interface ClassHourRepository extends JpaRepository<ClassHour, Integer>  {

	@Query
	boolean existsByBeginsAtBetweenAndRoomNo(int roomNo, LocalTime minusMinutes, LocalTime plusMinutes);

}
