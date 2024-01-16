package com.School.sba.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.School.sba.Exception.SchoolNotFoundException;
import com.School.sba.Repository.SchoolReposiory;
import com.School.sba.entity.School;
import com.School.sba.requestdto.SchoolRequestDTO;
import com.School.sba.responsedto.SchoolResponseDTO;
import com.School.sba.service.SchoolService;
import com.School.sba.utility.ResponseStructure;

@Service
public class SchoolServiceImpl implements SchoolService {

	@Autowired
	SchoolReposiory schoolReposiory;
	// method to convert SchoolRequestDTO type of object to School type of object
	public School mapToSchool(SchoolRequestDTO  schoolRequsetDTO )
	{
		return School.builder()
				.schoolName(schoolRequsetDTO.getSchoolName())
				.emailId(schoolRequsetDTO.getEmailId())
				.contactNo(schoolRequsetDTO.getContactNo())
				.address(schoolRequsetDTO.getAddress())
				.build();
	}
	// method to convert School type of object to SchoolRequestDTO type of object
	public SchoolResponseDTO mapToSchoolResponseDTO(School school)
	{
		return SchoolResponseDTO.builder()
				.schoolId(school.getSchoolId())
				.schoolName(school.getSchoolName())
				.contactNo(school.getContactNo())
				.emailId(school.getEmailId())
				.address(school.getAddress())
				.build();

	}

	//=====================================CREATE OPERATION=============================
	
	
	@Override
	public ResponseEntity<ResponseStructure<String>> addSchool(SchoolRequestDTO schoolRequestDTO) {

		School school=mapToSchool(schoolRequestDTO);
		schoolReposiory.save(school);
		ResponseStructure<String> responseStructure = new ResponseStructure();
		responseStructure.setStatusCode(HttpStatus.CREATED.value());
		responseStructure.setMessage("school details registered successfully");
		responseStructure.setData("school object added");
		return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.CREATED);

	}
	
	
	
	
	
	

	//=====================================READ OPERATION=============================
	
	// READ BY ID
	@Override
	public ResponseEntity<ResponseStructure<SchoolResponseDTO>> findSchoolById(int schoolId) {
		School school=schoolReposiory.findById(schoolId)
				.orElseThrow(()->new SchoolNotFoundException("School NOt Found"));
		SchoolResponseDTO schoolResponseDTO	=mapToSchoolResponseDTO(school);

		ResponseStructure<SchoolResponseDTO> responseStructure = new ResponseStructure();
		responseStructure.setStatusCode(HttpStatus.FOUND.value());
		responseStructure.setMessage("school with Id Found ");
		responseStructure.setData(schoolResponseDTO);

		return new ResponseEntity<ResponseStructure<SchoolResponseDTO>>(responseStructure,HttpStatus.FOUND);
	}
	
	// READ BY NAME
	@Override
	public ResponseEntity<ResponseStructure<List<SchoolResponseDTO>>> findSchoolByName(String schoolName) {
		List<School> schoolList=schoolReposiory.findSchoolByName(schoolName);
		List<SchoolResponseDTO> schoolResponse= new ArrayList<>();
		for (School school : schoolList) {
			SchoolResponseDTO schoolResponseDTO	=mapToSchoolResponseDTO(school);
			schoolResponse.add(schoolResponseDTO);
		}
		ResponseStructure<List<SchoolResponseDTO>> responseStructure = new ResponseStructure();
		responseStructure.setStatusCode(HttpStatus.FOUND.value());
		responseStructure.setMessage("school with Id Found ");
		responseStructure.setData(schoolResponse);
		
		return new ResponseEntity<ResponseStructure<List<SchoolResponseDTO>>>(responseStructure,HttpStatus.FOUND);
	}

	//=====================================UPDATE OPERATION=============================

	@Override
	public ResponseEntity<ResponseStructure<String>> updateSchoolById(School school, int schoolId) {
		//		Optional<School> optionalSchool=schoolReposiory.findById(schoolId);
		//		
		//		if(optionalSchool.isPresent())
		//		{
		//			
		//			School newSchool= optionalSchool.get();
		//			newSchool.setSchoolName(school.getSchoolName());
		//			newSchool.setContactNo(school.getContactNo());
		//			newSchool.setAddress(school.getAddress());
		//			schoolReposiory.save(newSchool);
		//			
		//			ResponseStructure<String> responseStructure = new ResponseStructure();
		//			 responseStructure.setStatusCode(HttpStatus.OK.value());
		//			 responseStructure.setMessage("school details updated successfully");
		//			 responseStructure.setData("School object updated");
		//			 
		//			 return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.OK);

		//		}
		//		
		//		else
		//		{
		//			throw new SchoolNotFoundException("School NOt Found");
		//			
		//		}
		//		//+++++++++++++++++++++++++++++++++++++++OR+++++++++++++++++++++++++++++++++++++
		//		//USING LAMBDA EXPRESSION
		//		School school1=schoolReposiory.findById(schoolId).orElseThrow(()->new SchoolNotFoundException("School NOt Found"));
		//		school1.setSchoolName(school.getSchoolName());
		//		school1.setContactNo(school.getContactNo());
		//		school1.setEmailId(school.getEmailId());
		//		school1.setAddress(school.getAddress());
		//		schoolReposiory.save(school1);
		//		ResponseStructure<String> responseStructure = new ResponseStructure();
		//		 responseStructure.setStatusCode(HttpStatus.OK.value());
		//		 responseStructure.setMessage("school details updated successfully");
		//		 responseStructure.setData("School object updated");

		//+++++++++++++++++++++++++++++++++++++++OR+++++++++++++++++++++++++++++++++++++
		//USING LAMBDA EXPRESSION and map(most feasible)
		School school1=schoolReposiory.findById(schoolId)
				.map( ex ->{
					ex.setSchoolName(school.getSchoolName());
					ex.setContactNo(school.getContactNo());
					ex.setEmailId(school.getEmailId());
					ex.setAddress(school.getAddress());
					return schoolReposiory.save(ex);

				}).orElseThrow(()->new SchoolNotFoundException("School NOt Found"));

		ResponseStructure<String> responseStructure = new ResponseStructure();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("school details updated successfully");
		responseStructure.setData("School object updated");

		return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.OK);
	}

	//=====================================DELETE OPERATION=============================

	@Override
	public ResponseEntity<ResponseStructure<String>> deleteSchoolById(int schoolId) {

		School School=schoolReposiory.findById(schoolId)
				.orElseThrow(()->new SchoolNotFoundException("School Not Found"));
					schoolReposiory.deleteById(schoolId);

					ResponseStructure<String> responseStructure = new ResponseStructure();
					responseStructure.setStatusCode(HttpStatus.OK.value());
					responseStructure.setMessage("school details Deleted successfully");
					responseStructure.setData("School Object Deleted");

					return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.OK);
				}
	

				
	}









