package com.School.sba.responsedto;

import com.School.sba.Enum.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data     
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

	private int userId;
	private String userName;
	private String firstName;
	private String lastName;
	private long contactNo;
	private String email;
	private UserRole userRole;
}
