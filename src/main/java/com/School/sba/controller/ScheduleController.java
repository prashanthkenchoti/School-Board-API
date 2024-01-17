package com.School.sba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.School.sba.requestdto.ScheduleRequestDTO;
import com.School.sba.responsedto.ScheduleResponseDTO;
import com.School.sba.service.ScheduleService;
import com.School.sba.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class ScheduleController {
	
	@Autowired
	private ScheduleService ScheduleRequestDTO;
	
	@PostMapping("/schools/{schoolId}/schedules")
	public ResponseEntity<ResponseStructure<ScheduleResponseDTO>> createSchedule(@RequestBody ScheduleRequestDTO scheduleRequestDTO,@PathVariable int schoolId)
	{
		return ScheduleRequestDTO.createSchedule(scheduleRequestDTO,schoolId);
	}
	
	
	@PostMapping("/schools/{schoolId}/schedules")
	public ResponseEntity<ResponseStructure<ScheduleResponseDTO>> findSchedule(@PathVariable int schoolId)
	{
		return ScheduleRequestDTO.createSchedule(schoolId);
	}

}
