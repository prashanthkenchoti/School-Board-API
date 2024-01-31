package com.School.sba.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.School.sba.Enum.UserRole;
import com.School.sba.requestdto.AcademicProgramRequestDTO;
import com.School.sba.responsedto.AcademicProgramResponseDTO;
import com.School.sba.responsedto.UserResponseDTO;
import com.School.sba.utility.ResponseStructure;

public interface AcademicProgramService {

	ResponseEntity<ResponseStructure<AcademicProgramResponseDTO>> addAcademicProgram(
			AcademicProgramRequestDTO academicProgramRequestDTO, int schoolId);

	ResponseEntity<ResponseStructure<List<AcademicProgramResponseDTO>>> findAllAcademicPrograms(int schoolId);

	ResponseEntity<ResponseStructure<String>> addTeacherAndStudentAP(int programId,
			int userId);

	
	ResponseEntity<ResponseStructure<List<UserResponseDTO>>> findAllUsersInAcademicProgram(int programId, UserRole role);

	ResponseEntity<ResponseStructure<AcademicProgramResponseDTO>> deleteAcademicProgramById(int programId);

}
