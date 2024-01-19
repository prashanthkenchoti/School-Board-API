package com.School.sba.requestdto;

import java.time.LocalTime;
import java.util.List;

import com.School.sba.entity.School;
import com.School.sba.entity.Subject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcademicProgramRequestDTO {
	
	private String programName;
	private LocalTime beginsAt;
	private LocalTime endsAt;
	private School school;
	private List<String> subjectList;
	
}