package com.School.sba.Exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ClassRoomNotFoundException extends RuntimeException {
	private String message;
	
	

}
