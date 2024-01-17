package com.School.sba.service;

import org.springframework.http.ResponseEntity;

import com.School.sba.requestdto.ScheduleRequestDTO;
import com.School.sba.responsedto.ScheduleResponseDTO;
import com.School.sba.utility.ResponseStructure;

public interface ScheduleService {

	ResponseEntity<ResponseStructure<ScheduleResponseDTO>> createSchedule(ScheduleRequestDTO scheduleRequestDTO, int schoolId);

	ResponseEntity<ResponseStructure<ScheduleResponseDTO>> findSchedule(int schoolId);

	ResponseEntity<ResponseStructure<ScheduleResponseDTO>> updateSchedule(ScheduleRequestDTO scheduleRequestDTO, int scheduleId);

}
