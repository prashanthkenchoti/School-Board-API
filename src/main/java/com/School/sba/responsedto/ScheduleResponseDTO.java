package com.School.sba.responsedto;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
public class ScheduleResponseDTO {
	private int scheduleId;
	private LocalTime opensAt;
	private LocalTime closesAt;
	private int classHoursPerDay;
	private Duration classHourLengthInMinute;
	private LocalTime breakTime;
	private Duration breakLengthInMinute;
	private LocalTime lunchTime;
	private Duration lunchLengthInMinute;
	

}
