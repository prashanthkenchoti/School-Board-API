package com.School.sba.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityProxy {

		public static <T> ResponseEntity<ResponseStructure<T>> getResponseEntity(HttpStatus status, String message, T data)
		{
			ResponseStructure<T> responseStructure = new ResponseStructure<T>();
			
			responseStructure.setStatusCode(status.value());
			responseStructure.setMessage(message);
			responseStructure.setData(data);
			
			return new ResponseEntity<ResponseStructure<T>>(responseStructure,status);
		}

}
