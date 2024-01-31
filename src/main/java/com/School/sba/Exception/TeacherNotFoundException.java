package com.School.sba.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeacherNotFoundException extends RuntimeException {
	
	private String message;

}
