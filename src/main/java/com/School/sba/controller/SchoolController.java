package com.School.sba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.School.sba.entity.School;
import com.School.sba.requestdto.SchoolRequestDTO;
import com.School.sba.responsedto.SchoolResponseDTO;
import com.School.sba.service.SchoolService;
import com.School.sba.utility.ResponseStructure;

import jakarta.validation.Valid;

@RestController
public class SchoolController {
	
	
	@Autowired
	SchoolService schoolService;
	
	// INSERT OPERATION
	
	@PostMapping("/schools")
	public ResponseEntity<ResponseStructure<String>> addSchool(@RequestBody @Valid SchoolRequestDTO schoolRequestDTO)
	{
		return schoolService.addSchool(schoolRequestDTO);
	}
	
	// READ OPERATION
	
	// READ BY ID
	
	@GetMapping("/schools/{schoolId}")
	public ResponseEntity<ResponseStructure<SchoolResponseDTO>> findSchoolById(@PathVariable int schoolId)
	{
		return schoolService.findSchoolById(schoolId);
	}
	
	// READ BY NAME
	@GetMapping("/school-names/{schoolName}/schools")
	public ResponseEntity<ResponseStructure<List<SchoolResponseDTO>>> findSchoolByName(@PathVariable String schoolName)
	{
		return schoolService.findSchoolByName(schoolName);
	}
	
	
	// UPDATE OPERATION
	
	@PutMapping("/schools/{schoolId}")
	public ResponseEntity<ResponseStructure<String>> updateSchoolById(@ RequestBody School school,@PathVariable int schoolId)
	{
		return schoolService.updateSchoolById(school,schoolId);
	}
	
	// DELETE OPERATION
	
	@DeleteMapping("/schools/{schoolId}")
	public ResponseEntity<ResponseStructure<String>> deleteSchoolById(@PathVariable int schoolId)
	{
		return schoolService.deleteSchoolById(schoolId);
	}
	

}
