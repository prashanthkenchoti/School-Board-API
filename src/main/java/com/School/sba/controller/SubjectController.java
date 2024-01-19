package com.School.sba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.School.sba.requestdto.SubjectRequestDTO;
import com.School.sba.responsedto.AcademicProgramResponseDTO;
import com.School.sba.responsedto.SubjectResponseDTO;
import com.School.sba.service.SubjectService;
import com.School.sba.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class SubjectController {


	@Autowired
	private SubjectService subjectService;

	@PostMapping("/academic-programs/{programId}/subjects")
	public ResponseEntity<ResponseStructure<AcademicProgramResponseDTO>> addSubject(@RequestBody SubjectRequestDTO subjectRequestDTO, int programId )
	{
		return subjectService.addSubject(subjectRequestDTO,programId);

	}
	
	@PostMapping("/academic-programs/{programId}")
	public ResponseEntity<ResponseStructure<AcademicProgramResponseDTO>> updateSubjects(@RequestBody SubjectRequestDTO subjectRequestDTO, int programId )
	{
		return subjectService.updateSubjects(subjectRequestDTO,programId);

	}
}
