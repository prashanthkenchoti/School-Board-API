package com.School.sba.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.School.sba.requestdto.ClassHourRequestDTO;
import com.School.sba.utility.ResponseStructure;

public interface ClassHourService {

	ResponseEntity<ResponseStructure<String>> genereateClassHoursForAcademicProgram(int programId);

	ResponseEntity<ResponseStructure<String>> updateClassHoursForAcademicProgram(
			List<ClassHourRequestDTO> classHourRequestDTO);

}
