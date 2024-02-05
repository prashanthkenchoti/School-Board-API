package com.School.sba.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.School.sba.entity.ClassHour;
import com.School.sba.requestdto.ClassHourRequestDTO;
import com.School.sba.requestdto.ExcelRequestDTO;
import com.School.sba.utility.ResponseStructure;

public interface ClassHourService  {

	ResponseEntity<ResponseStructure<String>> genereateClassHoursForAcademicProgram(int programId);

	Object updateClassHoursForAcademicProgram(
			List<ClassHourRequestDTO> classHourRequestDTO);

	ResponseEntity<ResponseStructure<List<ClassHour>>> craeteClassHoursForNextWeek(int programId);

	ResponseEntity<ResponseStructure<String>> printData(int programId, ExcelRequestDTO excelRequestDTO);

	ResponseEntity<?> writeTOExcel(MultipartFile file, int programId, LocalDate fromDate, LocalDate toDate)throws IOException;

}
