package com.School.sba.service;

import org.springframework.http.ResponseEntity;

import com.School.sba.requestdto.UserRequestDTO;
import com.School.sba.responsedto.UserResponseDTO;
import com.School.sba.utility.ResponseStructure;

public interface UserService {

	ResponseEntity<ResponseStructure<UserResponseDTO>> register(UserRequestDTO userRequestDTO);

	ResponseEntity<ResponseStructure<UserResponseDTO>> getUser(int userId);

	ResponseEntity<ResponseStructure<UserResponseDTO>> deleteUser(int userId);

}
