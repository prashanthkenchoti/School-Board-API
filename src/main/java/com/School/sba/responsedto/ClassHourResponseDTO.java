package com.School.sba.responsedto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassHourResponseDTO {
	
	private int classHourId;
	private  LocalTime beginsAt;
	private  LocalTime endsAt;

}
