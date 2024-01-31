package com.School.sba.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.School.sba.Enum.ClassStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class ClassHour {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int classHourId;
	private int roomNo;
	private  LocalDateTime beginsAt;
	private  LocalDateTime endsAt;
	
	private ClassStatus classStatus;
	
	@ManyToOne
	private AcademicProgram academicProgram;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Subject subject;
}
