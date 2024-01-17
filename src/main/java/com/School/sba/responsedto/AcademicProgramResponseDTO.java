package com.School.sba.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor@NoArgsConstructor
@Builder
public class AcademicProgramResponseDTO {
	
	private int programId;
	private String programName;
	private int beginsAt;
	private int endsAt;

}
