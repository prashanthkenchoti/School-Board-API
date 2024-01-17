package com.School.sba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.School.sba.requestdto.UserRequestDTO;
import com.School.sba.responsedto.UserResponseDTO;
import com.School.sba.service.UserService;
import com.School.sba.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/user/register")
	public ResponseEntity<ResponseStructure<UserResponseDTO>> register(@RequestBody UserRequestDTO userRequestDTO) {
		return userService.register(userRequestDTO);
	}
	
	@PostMapping("/users/{userId}/schools")
	public ResponseEntity<ResponseStructure<UserResponseDTO>> createSchool(@PathVariable int userId) {
		return userService.createSchool(userId);
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponseDTO>> getUser(@PathVariable int userId) {
		return userService.getUser(userId);
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponseDTO>> deleteUser(@PathVariable int userId) {
		return userService.deleteUser(userId);
	}

}
