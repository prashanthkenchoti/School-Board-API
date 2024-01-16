package com.School.sba.responsedto;

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
public class SchoolResponseDTO {
	
	private int schoolId;
	private String schoolName;
	private Long contactNo;
	private String emailId;
	private  String address;

}
