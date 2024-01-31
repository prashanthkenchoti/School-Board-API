package com.School.sba.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.School.sba.Enum.UserRole;
import com.School.sba.Exception.ConstraintVoilationException;
import com.School.sba.Exception.UserNotFoundException;
import com.School.sba.Repository.UserRepository;
import com.School.sba.entity.User;
import com.School.sba.requestdto.UserRequestDTO;
import com.School.sba.responsedto.UserResponseDTO;
import com.School.sba.service.UserService;
import com.School.sba.utility.ResponseStructure;

@Service
public class UserServiceImpl implements UserService  {
	
	@Autowired
	ResponseStructure<UserResponseDTO> responseStructure;

	@Autowired
	UserRepository userRepository;
	


	public User mapToUser(UserRequestDTO userRequestDTO) {
		return User.builder()
				.userName(userRequestDTO.getUserName())
				.password(userRequestDTO.getPassword())
				.firstName(userRequestDTO.getUserName())
				.lastName(userRequestDTO.getLastName()).
				contactNo(userRequestDTO.getContactNo())
				.email(userRequestDTO.getEmail())
				.userRole(userRequestDTO.getUserRole())
				.build();
	}

	public UserResponseDTO mapToUserResponse(User user) {
		return UserResponseDTO.builder()
				.userId(user.getUserId())
				.userName(user.getUserName())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.contactNo(user.getContactNo())
				.email(user.getEmail())
				.userRole(user.getUserRole())
				.build();

	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponseDTO>> register(UserRequestDTO userRequestDTO,int userId) {
	User  user = mapToUser(userRequestDTO);
		if(user.getUserRole().equals(UserRole.ADMIN))
		{
			
		try {
					user = userRepository.save(user);
			}
 
		catch(Exception ex)
		{
			throw new ConstraintVoilationException(HttpStatus.BAD_REQUEST.value(),"Duplicate entries Detected","please Use Unique entry ");
		}
	
	}
		responseStructure.setStatusCode(HttpStatus.CREATED.value());
		responseStructure.setMessage("User Registered Successfully");
		responseStructure.setData(mapToUserResponse(user));
		return new ResponseEntity<ResponseStructure<UserResponseDTO>>(responseStructure, HttpStatus.CREATED);
	}
	
	
	
	
	
	

	
	
	//==================================================================================================================================
		
	
	@Override
	public ResponseEntity<ResponseStructure<UserResponseDTO>> getUser(int userId) {
		Optional<User> user=userRepository.findById(userId);
		if(user.isPresent())
		{
			User responseUser=user.get();
			responseStructure.setStatusCode(HttpStatus.FOUND.value());
			responseStructure.setMessage("User Registered Successfully");
			responseStructure.setData(mapToUserResponse(responseUser));
			return new ResponseEntity<ResponseStructure<UserResponseDTO>>(responseStructure, HttpStatus.FOUND);	
		}
		else
		{
			throw new UserNotFoundException("user Not Found");
		}
	}

	
	
	//=====================================================================================================================================

	@Override
	public ResponseEntity<ResponseStructure<UserResponseDTO>> deleteUser(int userId) {
		Optional<User> user=userRepository.findById(userId);
		if(user.isPresent())
		{
			User responseUser=user.get();
			responseUser.setDeleted(true);
			userRepository.save(responseUser);
			responseStructure.setStatusCode(HttpStatus.FOUND.value());
			responseStructure.setMessage("User Deleted Successfully");
			responseStructure.setData(mapToUserResponse(responseUser));
			return new ResponseEntity<ResponseStructure<UserResponseDTO>>(responseStructure, HttpStatus.FOUND);	
		}
		else
		{
			throw new UserNotFoundException("user Not Found");
		}
			
		}

			
		
	}
