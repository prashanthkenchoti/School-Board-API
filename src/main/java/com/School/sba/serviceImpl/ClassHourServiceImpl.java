package com.School.sba.serviceImpl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.School.sba.Enum.ClassStatus;
import com.School.sba.Enum.UserRole;
import com.School.sba.Exception.AcademicProgramNotFoundException;
import com.School.sba.Exception.ClassHourNotFoundException;
import com.School.sba.Exception.ClassRoomNotFoundException;
import com.School.sba.Exception.ConstraintVoilationException;
import com.School.sba.Exception.ScheduleNotFoundException;
import com.School.sba.Exception.SubjectNotFoundException;
import com.School.sba.Exception.UserNotFoundException;
import com.School.sba.Repository.AcademicProgramRepository;
import com.School.sba.Repository.ClassHourRepository;
import com.School.sba.Repository.SubjectRepository;
import com.School.sba.Repository.UserRepository;
import com.School.sba.entity.ClassHour;
import com.School.sba.entity.Schedule;
import com.School.sba.entity.School;
import com.School.sba.entity.Subject;
import com.School.sba.entity.User;
import com.School.sba.requestdto.ClassHourRequestDTO;
import com.School.sba.responsedto.ClassHourResponseDTO;
import com.School.sba.responsedto.ScheduleResponseDTO;
import com.School.sba.service.ClassHourService;
import com.School.sba.utility.ResponseStructure;

@Service
public class ClassHourServiceImpl implements  ClassHourService {
	
	@Autowired
	private AcademicProgramRepository academicProgramRepository;
	
	@Autowired
	private ClassHourRepository classHourRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ResponseStructure<String> responseStructure;
	
	

	private boolean isBreakTime(LocalTime beginsAt, LocalTime endsAt, Schedule schedule)
	{
		LocalDateTime breakTimeStart = schedule.getBreakTime();
		
		return ((breakTimeStart.isAfter(beginsAt.toLocalTime()) && breakTimeStart.isBefore(endsAt.toLocalTime())) || breakTimeStart.equals(beginsAt.toLocalTime()));
	}
	
	private boolean isLunchTime(LocalDateTime beginsAt, LocalDateTime endsAt , Schedule schedule)
	{
		LocalDateTime lunchTimeStart = schedule.getLunchTime();
		
		return ((lunchTimeStart.isAfter(beginsAt.toLocalTime()) && lunchTimeStart.isBefore(endsAt.toLocalTime())) || lunchTimeStart.equals(beginsAt.toLocalTime()));
    }
	
	


	@Override
	public ResponseEntity<ResponseStructure<String>> genereateClassHoursForAcademicProgram(int programId) {
		
		 return academicProgramRepository.findById(programId).map(academicProgram -> {
			
			School school=academicProgram.getSchool();
			Schedule schedule= school.getSchedule();
			if(schedule!=null)
			{
				int classHourPerDay = schedule.getClassHoursPerDay();
				int classHourLength = (int) schedule.getClassHourLengthInMinute().toMinutes();
				
				LocalDateTime currentTime = LocalDateTime.now().with(schedule.getOpensAt());
				
				LocalDateTime lunchTimeStart = LocalDateTime.now().with(schedule.getLunchTime());
				LocalDateTime lunchTimeEnd = lunchTimeStart.plusMinutes(schedule.getLunchLengthInMinute().toMinutes());
				LocalDateTime breakTimeStart = LocalDateTime.now().with(schedule.getBreakTime());
				LocalDateTime breakTimeEnd = breakTimeStart.plusMinutes(schedule.getBreakLengthInMinute().toMinutes());
			
			for(int day=1; day<=6; day++)
			{
				for(int hour=1; hour<=classHourPerDay+2; hour++)
				{
					ClassHour classHour = new ClassHour();
					LocalDateTime beginsAt=currentTime;
					LocalDateTime endsAt= beginsAt.plusMinutes(classHourLength);
					
					if(!isLunchTime(beginsAt, endsAt, schedule))
					{
						if(!isBreakTime(beginsAt, endsAt, schedule))
						{
							classHour.setBeginsAt(beginsAt);
							classHour.setEndsAt(endsAt);
							classHour.setClassStatus(ClassStatus.NOT_SCHEDULED);
							
							currentTime = endsAt;
						}
						else
						{
							classHour.setBeginsAt(breakTimeStart);
							classHour.setEndsAt(breakTimeEnd);
							classHour.setClassStatus(ClassStatus.BREAK_TIME);
							currentTime = breakTimeEnd;
						}
					}
					else
					{
						classHour.setBeginsAt(lunchTimeStart);
						classHour.setEndsAt(lunchTimeEnd);
						classHour.setClassStatus(ClassStatus.LUNCH_TIME);
						currentTime = lunchTimeEnd;
					}
					classHour.setAcademicProgram(academicProgarm);
					classHourRepository.save(classHour);
				}
				currentTime = currentTime.plusDays(1).with(schedule.getOpensAt());
			}

		}
			
			
				}).orElseThrow(() -> new AcademicProgramNotFoundException("No such Academic program Exists"));
			}



	@Override
	public ResponseEntity<ResponseStructure<String>> updateClassHoursForAcademicProgram(
			List<ClassHourRequestDTO> classHourRequestDTO) {
		
		return classHourRequestDTO.forEach((request) ->{
			int userId=request.getUserId();
			userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
			
			int subjectId= request.getSubjectId();
		Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new SubjectNotFoundException(" subject not found"));
			
			int roomNo= request.getRoomNo();
			classHourRepository.findById(roomNo).orElseThrow(() -> new ClassRoomNotFoundException("Classroom Not found"));
			
			int classHourId= request.getClassHourId();
			ClassHour classHour= new ClassHour();
			classHourRepository.findById(classHourId).orElseThrow(() -> new ClassHourNotFoundException("classhour not Found"));
			if(!classHourRepository.existsByBeginsAtBetweenAndRoomNo(roomNo,classHour.getBeginsAt().minusMinutes(1),
					classHour.getBeginsAt().plusMinutes(1))) {
			
			User user=new User();
		if(user.getUserRole().equals(UserRole.TEACHER))
		{
			
			classHour.setUser(user);
			classHour.setSubject(subject);
			classHour.setRoomNo(roomNo);
			classHourRepository.save(classHour);
			
		}
		else
		{
			throw new ConstraintVoilationException(HttpStatus.BAD_REQUEST.value(),"constarint voilation","data validation failed");
		}
			}
			
			
		});
		
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("data accepted");
		responseStructure.setData("classHours Updated successfully");
	return ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.OK);
		
	}
		
	//=======================================================================================================================================
				
	public ResponseEntity<ResponseStructure<ClassHourResponseDTO>> deleteClassHour(List<ClassHour> classHours)
	{
		int scheduleId=schedule.getScheduleId();
		scheduleRepository.findById(scheduleId).orElseThrow(() -> new  ScheduleNotFoundException("Schedule Does not exist"));
		
		return null;
	}	
			

	

}
