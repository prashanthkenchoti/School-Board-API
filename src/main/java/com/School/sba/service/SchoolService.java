package com.School.sba.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.School.sba.entity.School;
import com.School.sba.requestdto.SchoolRequestDTO;
import com.School.sba.responsedto.SchoolResponseDTO;
import com.School.sba.utility.ResponseStructure;

public interface SchoolService {

	ResponseEntity<ResponseStructure<String>> addSchool(SchoolRequestDTO schoolRequestDTO);

	ResponseEntity<ResponseStructure<SchoolResponseDTO>> findSchoolById(int schoolId);

	ResponseEntity<ResponseStructure<String>> updateSchoolById(School school, int schoolId);

	ResponseEntity<ResponseStructure<String>> deleteSchoolById(int schoolId);

	ResponseEntity<ResponseStructure<List<SchoolResponseDTO>>> findSchoolByName(String schoolName);

}