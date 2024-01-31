package com.School.sba.responsedto;

import java.sql.Time;
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
	private LocalDateTime opensAt;
	private LocalDateTime closesAt;
	private int classHoursPerDay;
	private int classHourLengthInMinute;
	private LocalDateTime breakTime;
	private int breakLengthInMinute;
	private LocalDateTime lunchTime;
	private int lunchLengthInMinute;
	

}
