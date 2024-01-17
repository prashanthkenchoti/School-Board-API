package com.School.sba.requestdto;

import com.School.sba.Enum.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
	
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private long contactNo;
	private String email;
	private UserRole userRole;

}
