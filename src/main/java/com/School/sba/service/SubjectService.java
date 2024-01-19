package com.School.sba.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.School.sba.requestdto.SubjectRequestDTO;
import com.School.sba.responsedto.AcademicProgramResponseDTO;
import com.School.sba.responsedto.SubjectResponseDTO;
import com.School.sba.utility.ResponseStructure;

public interface SubjectService {

	
	ResponseEntity<ResponseStructure<AcademicProgramResponseDTO>> addSubject(SubjectRequestDTO subjectRequestDTO,
			int programId);

	ResponseEntity<ResponseStructure<AcademicProgramResponseDTO>> updateSubjects(SubjectRequestDTO subjectRequestDTO,
			int programId);

	ResponseEntity<ResponseStructure<List<SubjectResponseDTO>>> findAllSubjects();

	ResponseEntity<ResponseStructure<String>> addSubjectTOTeacher(int subjectId, int userId);

	
}
