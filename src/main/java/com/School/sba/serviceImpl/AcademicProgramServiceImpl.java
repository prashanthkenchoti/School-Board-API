package com.School.sba.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.School.sba.Enum.UserRole;
import com.School.sba.Exception.AcademicProgramNotFoundException;
import com.School.sba.Exception.SchoolNotFoundException;
import com.School.sba.Exception.UnAuthorisedAccessException;
import com.School.sba.Exception.UserNotFoundException;
import com.School.sba.Repository.AcademicProgramRepository;
import com.School.sba.Repository.SchoolReposiory;
import com.School.sba.Repository.UserRepository;
import com.School.sba.entity.AcademicProgram;
import com.School.sba.entity.Subject;
import com.School.sba.entity.User;
import com.School.sba.requestdto.AcademicProgramRequestDTO;
import com.School.sba.responsedto.AcademicProgramResponseDTO;
import com.School.sba.service.AcademicProgramService;
import com.School.sba.utility.ResponseStructure;

@Service
public class AcademicProgramServiceImpl implements AcademicProgramService {

	@Autowired
	private AcademicProgramRepository academicProgramRepository;

	@Autowired
	SchoolReposiory schoolRepository;

	@Autowired
	ResponseStructure<AcademicProgramResponseDTO> responseStructure;
	
	@Autowired
	ResponseStructure<String> ResponseStructure;

	@Autowired
	ResponseStructure<List<AcademicProgramResponseDTO>> academicProgramResponseList;
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	private User user;
	
	@Autowired
	UserRepository userRepository;

	private AcademicProgram mapToAcademicProgram(AcademicProgramRequestDTO academicProgramRequestDTO) {
		return AcademicProgram.builder().programName(academicProgramRequestDTO.getProgramName())
				.beginsAt(academicProgramRequestDTO.getBeginsAt()).endsAt(academicProgramRequestDTO.getEndsAt())
				.build();
	}

	public AcademicProgramResponseDTO maptoAcademicProgramResponseDTO(AcademicProgram academicProgram) {
		
		List<String> subjectNames = new ArrayList<>();
		academicProgram.getSubjectList().forEach(subject ->{
			subjectNames.add(subject.getSubjectName());
			
		});
		
		return AcademicProgramResponseDTO.builder()
				.programId(academicProgram.getProgramId())
				.programName(academicProgram.getProgramName())
				.beginsAt(academicProgram.getBeginsAt())
				.endsAt(academicProgram.getEndsAt())
				.subjectList(subjectNames)
				.build();
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

//================================================================================================================================	

	@Override
	public ResponseEntity<ResponseStructure<String>> addTeacherAndStudentAP(int programId,
			int userId) {
	User newUser =	userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("user not Found"));
	 AcademicProgram program =	academicProgramRepository.findById(programId).orElseThrow(() -> new AcademicProgramNotFoundException("program does not Exist"));
			if(!user.getUserRole().equals(UserRole.ADMIN))
			{
				program.getUserList().add(newUser);	
				academicProgramRepository.save(program);
				ResponseStructure.setStatusCode(HttpStatus.OK.value());
				ResponseStructure.setMessage("Student and Teachers Added Successfully");
				ResponseStructure.setData("Student and Teachers Saved Successfully");
				return new ResponseEntity<ResponseStructure<String>>(ResponseStructure,
						HttpStatus.OK);
			}
			else
			{
				throw new UnAuthorisedAccessException("User Not Allowed");
			}
	}
	
	
}
