package com.School.sba.utility;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorStructure<T> {
	
	private int statusCode;
	private String rootCause;
	private T data;

}
