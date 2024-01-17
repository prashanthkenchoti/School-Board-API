package com.School.sba.requestdto;

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
	private int beginsAt;
	private int endsAt;

}
