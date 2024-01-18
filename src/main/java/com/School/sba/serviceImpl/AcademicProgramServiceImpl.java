package com.School.sba.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.School.sba.Enum.UserRole;
import com.School.sba.Exception.SchoolNotFoundException;
import com.School.sba.Repository.AcademicProgramRepository;
import com.School.sba.Repository.SchoolReposiory;
import com.School.sba.entity.AcademicProgram;
import com.School.sba.entity.School;
import com.School.sba.requestdto.AcademicProgramRequestDTO;
import com.School.sba.responsedto.AcademicProgramResponseDTO;
import com.School.sba.responsedto.ScheduleResponseDTO;
import com.School.sba.service.AcademicProgramService;
import com.School.sba.utility.ResponseStructure;

import lombok.Builder;

@Service
public class AcademicProgramServiceImpl implements AcademicProgramService {

	@Autowired
	private AcademicProgramRepository academicProgramRepository;

	@Autowired
	SchoolReposiory schoolRepository;

	@Autowired
	ResponseStructure<AcademicProgramResponseDTO> responseStructure;

	@Autowired
	ResponseStructure<List<AcademicProgramResponseDTO>> academicProgramResponseList;

	private AcademicProgram mapToAcademicProgram(AcademicProgramRequestDTO academicProgramRequestDTO) {
		return AcademicProgram.builder().programName(academicProgramRequestDTO.getProgramName())
				.beginsAt(academicProgramRequestDTO.getBeginsAt()).endsAt(academicProgramRequestDTO.getEndsAt())
				.build();
	}

	public AcademicProgramResponseDTO maptoAcademicProgramResponseDTO(AcademicProgram academicProgram) {
		return AcademicProgramResponseDTO.builder().programId(academicProgram.getProgramId())
				.programName(academicProgram.getProgramName()).beginsAt(academicProgram.getBeginsAt())
				.endsAt(academicProgram.getEndsAt()).build();
	}

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponseDTO>> addAcademicProgram(
			AcademicProgramRequestDTO academicProgramRequestDTO, int schoolId) {

		return schoolRepository.findById(schoolId).map(p -> {
			AcademicProgram academicProgram = mapToAcademicProgram(academicProgramRequestDTO);
			academicProgramRepository.save(academicProgram);
			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			responseStructure.setMessage("Schedule set successfully");
			responseStructure.setData(maptoAcademicProgramResponseDTO(academicProgram));
			return new ResponseEntity<ResponseStructure<AcademicProgramResponseDTO>>(responseStructure,
					HttpStatus.CREATED);

		}).orElseThrow(() -> new SchoolNotFoundException("School Does Not Exist"));

	}

	// ==============================================================================================================================

	@Override
	public ResponseEntity<ResponseStructure<List<AcademicProgramResponseDTO>>> findAllAcademicPrograms(int schoolId) {
		List<AcademicProgramResponseDTO> responselist = new ArrayList<>();
		return schoolRepository.findById(schoolId).map(k -> {
			if (!k.getProgramList().isEmpty()) {

				for (AcademicProgram academicProgram : k.getProgramList()) {

					responselist.add(maptoAcademicProgramResponseDTO(academicProgram));
				}

			}
			academicProgramResponseList.setStatusCode(HttpStatus.FOUND.value());
			academicProgramResponseList.setMessage("Academic Programs Retrieved Successfully");
			academicProgramResponseList.setData(responselist);
			return new ResponseEntity<ResponseStructure<List<AcademicProgramResponseDTO>>>(academicProgramResponseList,
					HttpStatus.FOUND);

		}).orElseThrow(() -> new SchoolNotFoundException("School Not Exists"));

	}

}
