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

	@org.springframework.web.bind.annotation.ExceptionHandler(ScheduleExistsException.class)
	public ResponseEntity<Object> scheduleExists(ScheduleExistsException ScheduleExistsException )
	{
		return responseStructure(HttpStatus.IM_USED,ScheduleExistsException.getMessage(),"already School Has A Schedule");
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ScheduleNotFoundException.class)
	public ResponseEntity<Object> scheduleNotFound(ScheduleNotFoundException sheduleNotFoundException )
	{
		return responseStructure(HttpStatus.NO_CONTENT,sheduleNotFoundException.getMessage(),"the School Does Not Have Any Associated Schedules With it");
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(AcademicProgramNotFoundException.class)
	public ResponseEntity<Object> AcademicProgramNotFound(AcademicProgramNotFoundException academicProgramNotFoundException )
	{
		return responseStructure(HttpStatus.NO_CONTENT,academicProgramNotFoundException.getMessage(),"the Program with Id is Not Found");
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(UnAuthorisedAccessException.class)
	public ResponseEntity<Object> unAuthorisedAccess(UnAuthorisedAccessException unAuthorisedAccessException )
	{
		return responseStructure(HttpStatus.NOT_FOUND,unAuthorisedAccessException.getMessage(),"User Not Allowed To Perform This Operation");
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(SubjectNotFoundException.class)
	public ResponseEntity<Object> subjectNotFound(SubjectNotFoundException subjectNotFoundException )
	{
		return responseStructure(HttpStatus.NOT_FOUND,subjectNotFoundException.getMessage(),"No Subjects Are Found ");
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(ClassRoomNotFoundException.class)
	public ResponseEntity<Object> classRoomNotFound(ClassRoomNotFoundException classRoomNotFoundException )
	{
		return responseStructure(HttpStatus.NOT_FOUND,classRoomNotFoundException.getMessage()," all clssrooms are already engaged ");
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(ClassRoomNotFoundException.class)
	public ResponseEntity<Object> classHourNotFound(ClassHourNotFoundException classHourNotFoundException )
	{
		return responseStructure(HttpStatus.NOT_FOUND,classHourNotFoundException.getMessage()," class hour is not planned for the day ");
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(ClassRoomNotFoundException.class)
	public ResponseEntity<Object> classHourNotFound(TeacherNotFoundException teacherNotFoundException )
	{
		return responseStructure(HttpStatus.NOT_FOUND,teacherNotFoundException.getMessage()," academic program does not assigned with any teachers ");
	}
}
