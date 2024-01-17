package com.School.sba.Exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConstraintVoilationException extends RuntimeException {
	
	private int StatusCode;
	private String message;
	private String rootCause;

}
