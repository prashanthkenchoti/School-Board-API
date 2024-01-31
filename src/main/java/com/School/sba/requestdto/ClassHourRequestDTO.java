package com.School.sba.requestdto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassHourRequestDTO {
	
	private int classHourId;
	private  LocalTime beginsAt;
	private  LocalTime endsAt;
	private int subjectId;
	private int userId;
	private int roomNo;

}
