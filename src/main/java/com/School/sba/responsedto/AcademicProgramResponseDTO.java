package com.School.sba.responsedto;

import java.time.LocalTime;
import java.util.List;

import com.School.sba.entity.School;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor@NoArgsConstructor
@Builder
public class AcademicProgramResponseDTO {
	
	private int programId;
	private String programName;
	private LocalTime beginsAt;
	private LocalTime endsAt;
	private School school;
	private List<String> subjectList;
	
}
