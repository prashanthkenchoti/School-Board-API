package com.School.sba.serviceImpl;

import java.lang.foreign.Linker.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.School.sba.Exception.AcademicProgramNotFoundException;
import com.School.sba.Repository.AcademicProgramRepository;
import com.School.sba.Repository.SubjectRepository;
import com.School.sba.entity.Subject;
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


	
	private Subject mapToSubject( SubjectRequestDTO subjectRequestDTO)
	{
		return Subject.builder()
				.subjectNames(subjectRequestDTO.getSubjectName())
				.build();
	}
		
	private SubjectResponseDTO mapToSubjectResponseDTO(Subject subject  )
	{
		return SubjectResponseDTO.builder()
				.subjectId(subject.getSubjectId())
				.subjectName(subject.getSubjectNames())
				.build();
	}

	
		@Override
		public ResponseEntity<ResponseStructure<AcademicProgramResponseDTO>> addSubject(SubjectRequestDTO subjectRequestDTO,
				int programId) {
			return academicProgramRepository.findById(programId).map(program -> {
				List<Subject> subjects=new ArrayList<>();
				subjectRequestDTO.getSubjectName().forEach(name ->{
				Subject subject =	subjectRepository.findSubjectByName(name).map(s -> s) 
						.orElseGet(() -> {
						Subject sub = new Subject();
						sub.setSubjectName(name);
						subjectRepo.save(subject);
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
		}


				
	

