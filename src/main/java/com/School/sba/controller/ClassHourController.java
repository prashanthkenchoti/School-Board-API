package com.School.sba.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.School.sba.entity.ClassHour;
import com.School.sba.requestdto.ClassHourRequestDTO;
import com.School.sba.requestdto.ExcelRequestDTO;
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
	
	// write in excelsheet for standalone Application
	@PostMapping("/academic-programs/{programId}/class-hours/write-excel")
	public ResponseEntity<ResponseStructure<String>> printData(@PathVariable int programId,@RequestBody ExcelRequestDTO  excelRequestDTO  )
	{
		return classHourService.printData(programId,excelRequestDTO);
	
	}
	
	@PostMapping("/academic-programs/{programId}/class-hours/from/{fromDate}/to/{toDate}/write-excel")
	public ResponseEntity<?> writeTOExcel(@RequestParam MultipartFile file, @PathVariable int programId,@PathVariable LocalDate fromDate,@PathVariable LocalDate toDate )throws IOException
	{
		return classHourService.writeTOExcel(file,programId,fromDate,toDate);
	}

}
