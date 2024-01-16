package com.School.sba.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SchoolNotFoundException extends RuntimeException {
	
	private String message;
	
	
	
	

}
