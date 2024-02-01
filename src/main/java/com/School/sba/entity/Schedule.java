package com.School.sba.entity;


import java.time.Duration;
import java.time.LocalTime;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
public class Schedule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int scheduleId;
	private LocalTime opensAt;
	private LocalTime closesAt;
	private int classHoursPerDay;
	private Duration classHourLengthInMinute;
	private LocalTime breakTime;
	private Duration breakLengthInMinute;
	private LocalTime lunchTime;
	private Duration lunchLengthInMinute;
	
	
	
//	private School school;
	
	

}
