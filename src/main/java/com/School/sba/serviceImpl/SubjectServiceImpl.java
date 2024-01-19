package com.School.sba.serviceImpl;

import java.lang.foreign.Linker.Option;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.School.sba.Enum.UserRole;
import com.School.sba.Exception.AcademicProgramNotFoundException;
import com.School.sba.Exception.SubjectNotFoundException;
import com.School.sba.Exception.UnAuthorisedAccessException;
import com.School.sba.Exception.UserNotFoundException;
import com.School.sba.Repository.AcademicProgramRepository;
import com.School.sba.Repository.SubjectRepository;
import com.School.sba.Repository.UserRepository;
import com.School.sba.entity.Subject;
import com.School.sba.entity.User;
import com.School.sba.requestdto.SubjectRequestDTO;
import com.School.sba.responsedto.AcademicProgramResponseDTO;
import com.School.sba.responsedto.SubjectResponseDTO;
import com.School.sba.service.SubjectService;
import com.School.sba.utility.ResponseStructure;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectRepository  subjectRepository;

	@Autowired
	private AcademicProgramRepository academicProgramRepository;

	@Autowired
	private ResponseStructure<AcademicProgramResponseDTO> responseStructure;

	@Autowired
	private AcademicProgramServiceImpl  academicProgramServiceImpl;
	
    @Autowired
	private ResponseStructure<List<SubjectResponseDTO>> ResponseStructure;
	
    @Autowired
    private ResponseStructure<String> structure;
    
    @Autowired
    private UserRepository userRepository;


	private SubjectResponseDTO  mapToSubjectResponseDTO(Subject subject)
	{
		return new SubjectResponseDTO().builder()
				.subjectId(subject.getSubjectId())

				.subjectName(subject.getSubjectName())
				.build();
	}



	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponseDTO>> addSubject(SubjectRequestDTO subjectRequestDTO,
			int programId) {
		return academicProgramRepository.findById(programId).map(program -> {
			List<Subject> subjects=new ArrayList<>();
			subjectRequestDTO.getSubjectNames().forEach(name ->{
				Subject subject =	subjectRepository.findSubjectByName(name).map(s -> s) 
						.orElseGet(() -> {
							Subject sub = new Subject();
							sub.setSubjectName(name);
							subjectRepository.save(sub);
							subjects.add(sub);
							return sub;
						});
				subjects.add(subject);

			});
			program.setSubjectList(subjects);
			academicProgramRepository.save(program);
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("updated the subjectlist to academic programme");
			responseStructure.setData(academicProgramServiceImpl.maptoAcademicProgramResponseDTO(program));
			return new  ResponseEntity<ResponseStructure<AcademicProgramResponseDTO>> (responseStructure, HttpStatus.OK);
		}).orElseThrow(()-> new AcademicProgramNotFoundException("Errror While Applying the data "));


	}






	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponseDTO>> updateSubjects(
			SubjectRequestDTO subjectRequestDTO, int programId) {
		// TODO Auto-generated method stub
		return null;
	}






	@Override
	public ResponseEntity<ResponseStructure<List<SubjectResponseDTO>>> findAllSubjects() {
		List<Subject> subjects =	subjectRepository.findAll();
		if(subjects.isEmpty())
		{
			throw new SubjectNotFoundException("there are no subjects to show");
		}
		else
		{
			List<SubjectResponseDTO> responsesubject = new ArrayList<>();
			for (Subject subject : subjects) {
				responsesubject.add(mapToSubjectResponseDTO(subject));
			}
			ResponseStructure.setStatusCode(HttpStatus.OK.value());
			ResponseStructure.setMessage("All Subjects retrieved Successfully");
			ResponseStructure.setData(responsesubject);
			return new ResponseEntity<ResponseStructure<List<SubjectResponseDTO>>>(ResponseStructure,HttpStatus.OK);
		}

	}



	@Override
	public ResponseEntity<ResponseStructure<String>> addSubjectTOTeacher(int subjectId,
			int userId) {
		User user= userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User Not Found"));
		Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new SubjectNotFoundException("Subject Not Exixt"));
			if(user.getUserRole().equals(UserRole.TEACHER))
			{
				subject.getUser().add(user);
				subjectRepository.save(subject);
				structure.setStatusCode(HttpStatus.OK.value());
				structure.setMessage("Subject is Found Successfully");
				structure.setData("subject is assigned to the given userId ");
				return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.OK);
			}
			else
			{
				throw new UnAuthorisedAccessException("User can Not Be Assigned With Subject");
			}
		
				
	}


	


}





