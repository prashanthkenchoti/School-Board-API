package com.School.sba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.School.sba.requestdto.ClassHourRequestDTO;
import com.School.sba.service.ClassHourService;
import com.School.sba.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class ClassHourController {
	
	@Autowired
	private ClassHourService classHourService;
	
	@PostMapping("/academic-program/{programId}/class-hours")
	public ResponseEntity<ResponseStructure<String>> genereateClassHoursForAcademicProgram(@PathVariable int programId)
	{
		return classHourService.genereateClassHoursForAcademicProgram(programId);
	}

	
	@PutMapping("/class-hours")
	public Object updateClassHoursForAcademicProgram(@RequestBody List<ClassHourRequestDTO> classHourRequestDTO)
	{
		return classHourService.updateClassHoursForAcademicProgram(classHourRequestDTO);
	}

}
