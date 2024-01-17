package com.School.sba.Exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.School.sba.entity.School;
import com.School.sba.utility.ErrorStructure;
import com.School.sba.utility.ResponseStructure;

@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		List<ObjectError> allErrors=ex.getAllErrors();
		Map<String, String> errors= new HashMap<String,String>();
		allErrors.forEach(error ->{
			FieldError fieldError =(FieldError) error;
			errors.put(fieldError.getField(),fieldError.getDefaultMessage());
		});
		return responseStructure(HttpStatus.BAD_REQUEST,ex.getMessage(),errors );
	}
	
	private ResponseEntity<Object> responseStructure(HttpStatus status,String message,Object rootCause)
	{
		return new ResponseEntity<Object>(Map.of(
				"status",status.value(),
				"message",message,
				"rootcause",rootCause
				),status);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(SchoolNotFoundException.class)
	public ResponseEntity<Object> schoolNotFound(SchoolNotFoundException ex)
	{
		
		 return responseStructure(HttpStatus.NOT_FOUND, ex.getMessage(), "School Not Found With Given Id");
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> userNotFound(UserNotFoundException userNotFoundException )
	{
		return responseStructure(HttpStatus.NOT_FOUND,userNotFoundException.getMessage(),"User Not Found With Given Id");
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> scheduleExists(ScheduleExistsException ScheduleExistsException )
	{
		return responseStructure(HttpStatus.IM_USED,ScheduleExistsException.getMessage(),"already School Has A Schedule");
	}
	

}
