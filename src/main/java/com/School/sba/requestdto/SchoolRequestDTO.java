package com.School.sba.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolRequestDTO {
	
	@NotEmpty(message = "School can not  be null")
	@Pattern(regexp="^[A-Z][a-zA-Z]([ ]?[A-Z][a-zA-Z])*$",message = "First name of the letter canot be Small it should be "
			+ "pascle casing with Space")
	private String schoolName;
	@NotNull(message = "Contact Number can not be Null")
	@Min(value = 6000000000l, message = " phone number must be valid")
	@Max(value = 9999999999l, message = " phone number must be valid")
	private Long contactNo;
	@NotNull(message = "Email can not be Null")
	@NotBlank(message = "Email is required")
	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ")
	private String emailId;
	@NotBlank(message="Address can Not Be null")
	private  String address;
	
//	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
//	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must"
//			+ " contain at least one letter, one number, one special character")
//		private String Password;
}
