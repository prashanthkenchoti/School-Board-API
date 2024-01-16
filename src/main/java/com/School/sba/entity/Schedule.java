package com.School.sba.entity;

import java.sql.Time;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class Schedule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int scheduleId;
	private Time opensAt;
	private Time closesAt;
	private int classHoursPerDay;
	private int classHourLength;
	private Time breakTime;
	private Time breakLength;
	private Time lunchTime;
	private Time lunchLength;
	
	
//	private School school;
	
	

}
