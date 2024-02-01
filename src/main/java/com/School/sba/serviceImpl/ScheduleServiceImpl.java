package com.School.sba.serviceImpl;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.School.sba.Enum.UserRole;
import com.School.sba.Exception.ScheduleExistsException;
import com.School.sba.Exception.ScheduleNotFoundException;
import com.School.sba.Exception.SchoolNotFoundException;
import com.School.sba.Exception.UserNotFoundException;
import com.School.sba.Repository.ClassHourRepository;
import com.School.sba.Repository.ScheduleRepository;
import com.School.sba.Repository.SchoolReposiory;
import com.School.sba.Repository.UserRepository;
import com.School.sba.entity.ClassHour;
import com.School.sba.entity.Schedule;
import com.School.sba.entity.School;
import com.School.sba.entity.User;
import com.School.sba.requestdto.ScheduleRequestDTO;
import com.School.sba.requestdto.SchoolRequestDTO;
import com.School.sba.responsedto.ScheduleResponseDTO;
import com.School.sba.responsedto.SchoolResponseDTO;
import com.School.sba.service.ScheduleService;
import com.School.sba.utility.ResponseStructure;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	ScheduleRepository scheduleRepository;
	@Autowired
	SchoolReposiory schoolReposiory;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ClassHourRepository classHourRepository;

	@Autowired
	ResponseStructure<ScheduleResponseDTO> responseStructure;

	public Schedule mapToSchedule(ScheduleRequestDTO  scheduleRequestDTO )
	{
		return Schedule.builder()
				.opensAt(scheduleRequestDTO.getOpensAt())
				.closesAt(scheduleRequestDTO.getClosesAt())
				.classHoursPerDay(scheduleRequestDTO.getClassHoursPerDay())
				.classHourLengthInMinute(scheduleRequestDTO.getClassHourLengthInMinute())
				.breakTime(scheduleRequestDTO.getBreakTime())
				.breakLengthInMinute(scheduleRequestDTO.getBreakLengthInMinute())
				.lunchTime(scheduleRequestDTO.getLunchTime())
				.lunchLengthInMinute(scheduleRequestDTO.getLunchLengthInMinute())
				.build();
	}




	// method to convert School type of object to SchoolRequestDTO type of object
	public ScheduleResponseDTO mapToScheduleResponseDTO(Schedule schedule)
	{
		return ScheduleResponseDTO.builder()
				.scheduleId(schedule.getScheduleId())
				.opensAt(schedule.getOpensAt())
				.closesAt(schedule.getClosesAt())
				.classHoursPerDay(schedule.getClassHoursPerDay())
				.classHourLengthInMinute(schedule.getClassHourLengthInMinute())
				.breakTime(schedule.getBreakTime())
				.breakLengthInMinute(schedule.getBreakLengthInMinute())
				.lunchTime(schedule.getLunchTime())
				.lunchLengthInMinute(schedule.getLunchLengthInMinute())
				.build();

	}


	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponseDTO>> createSchedule(ScheduleRequestDTO scheduleRequestDTO,int schoolId) {
		return schoolReposiory.findById(schoolId).map(school -> {
			if(school.getSchedule()==null)
			{
				Schedule schedule=mapToSchedule(scheduleRequestDTO);
				schedule = scheduleRepository.save(schedule);
				school.setSchedule(schedule);
				schoolReposiory.save(school);
				responseStructure.setStatusCode(HttpStatus.CREATED.value());
				responseStructure.setMessage("Schedule set successfully");
				responseStructure.setData(mapToScheduleResponseDTO(schedule));
				return new ResponseEntity<ResponseStructure<ScheduleResponseDTO>>(responseStructure,HttpStatus.CREATED);
			}
			else {
				throw new ScheduleExistsException("More than one Schedule can not be Accepted");
			}
		}).orElseThrow(()-> new SchoolNotFoundException("No such school Exists") );

	}

	//================================================================================================================================

	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponseDTO>> findSchedule(int schoolId) {
		return schoolReposiory.findById(schoolId).map(u ->{
			if(u.getSchedule()!=null)
			{
				Schedule schedule=u.getSchedule();
				responseStructure.setStatusCode(HttpStatus.FOUND.value());
				responseStructure.setMessage("Schedule Accessed successfully");
				responseStructure.setData(mapToScheduleResponseDTO(schedule));
				return new ResponseEntity<ResponseStructure<ScheduleResponseDTO>>(responseStructure,HttpStatus.FOUND);


			}
			else {
				throw new ScheduleNotFoundException("Schedule Does Not Existed");

			}

		}).orElseThrow(()-> new SchoolNotFoundException("School Does not Exist"));

	}

	//================================================================================================================================

	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponseDTO>> updateSchedule(ScheduleRequestDTO scheduleRequestDTO ,int scheduleId) {
		return scheduleRepository.findById(scheduleId).map(m ->{
			Schedule schedule=mapToSchedule(scheduleRequestDTO);
			scheduleRepository.save(schedule);
			responseStructure.setStatusCode(HttpStatus.FOUND.value());
			responseStructure.setMessage("Schedule updated successfully");
			responseStructure.setData(mapToScheduleResponseDTO(schedule));
			return new ResponseEntity<ResponseStructure<ScheduleResponseDTO>>(responseStructure,HttpStatus.FOUND);

		}).orElseThrow(()-> new ScheduleNotFoundException());

		
	}
	
	//===============================================================================================================================
	
	public ResponseEntity<ResponseStructure<ScheduleResponseDTO>> deleteSchedule(Schedule schedule)
	{
		int scheduleId=schedule.getScheduleId();
	Schedule scheduleReturn =	scheduleRepository.findById(scheduleId).orElseThrow(() -> new  ScheduleNotFoundException("Schedule Does not exist"));
		
		scheduleRepository.delete(schedule);
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("schedule is deleted for the school");
		responseStructure.setData(mapToScheduleResponseDTO(scheduleReturn));
		return new ResponseEntity<ResponseStructure<ScheduleResponseDTO>>(responseStructure,HttpStatus.OK);
	}



}


