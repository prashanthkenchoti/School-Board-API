package com.School.sba.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.School.sba.entity.ClassHour;
import com.School.sba.entity.User;


public interface ClassHourRepository extends JpaRepository<ClassHour, Integer>  {

	
//	boolean existsByBeginsAtBetweenAndRoomNo(int roomNo, LocalTime minusMinutes, LocalTime plusMinutes);
//
	List<ClassHour> findByUser(User b);
//
	boolean existsByRoomNoAndBeginsAtBetween(int roomNo, LocalDateTime minusMinutes, LocalDateTime plusMinutes);

}
