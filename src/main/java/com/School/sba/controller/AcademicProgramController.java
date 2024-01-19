package com.School.sba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.School.sba.requestdto.AcademicProgramRequestDTO;
import com.School.sba.responsedto.AcademicProgramResponseDTO;
import com.School.sba.service.AcademicProgramService;
import com.School.sba.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class AcademicProgramController {
	
	@Autowired
	private AcademicProgramService academicProgramService;
	
	@PostMapping("/schools/{schoolId}/academic-programs")
	public ResponseEntity<ResponseStructure<AcademicProgramResponseDTO>> addAcademicProgram(@RequestBody AcademicProgramRequestDTO academicProgramRequestDTO, @PathVariable int schoolId )
	{
		return academicProgramService.addAcademicProgram(academicProgramRequestDTO,schoolId);
	}
	
	
	@GetMapping("/schools/{schoolId}/academic-programs")
	public ResponseEntity<ResponseStructure<List<AcademicProgramResponseDTO>>> findAllAcademicPrograms( @PathVariable int schoolId )
	{
		return academicProgramService.findAllAcademicPrograms(schoolId);
	}
	
	
	@PutMapping("/academic-programs/{programId}/users/{userId}")
	public ResponseEntity<ResponseStructure<String>> addTeacherAndStudentAP( @PathVariable int programId, @PathVariable int userId)
	{
		return academicProgramService.addTeacherAndStudentAP(programId,userId);
	}
}
