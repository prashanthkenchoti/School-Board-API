package com.School.sba.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcademicProgramNotFoundException extends RuntimeException{
	
	private String message;

}
