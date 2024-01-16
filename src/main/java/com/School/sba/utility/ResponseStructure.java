package com.School.sba.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseStructure<T> {
	
	public ResponseStructure() {
		// TODO Auto-generated constructor stub
	}
	private int statusCode;
	private String message;
	private T data;
	

}
