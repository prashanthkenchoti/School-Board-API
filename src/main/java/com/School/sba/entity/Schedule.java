package com.School.sba.entity;


import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Builder
public class Schedule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int scheduleId;
	private LocalDateTime opensAt;
	private LocalDateTime closesAt;
	private int classHoursPerDay;
	private int classHourLengthInMinute;
	private LocalDateTime breakTime;
	private int breakLengthInMinute;
	private LocalDateTime lunchTime;
	private int lunchLengthInMinute;
	
	private School school;
	
	
//	private School school;
	
	

}
