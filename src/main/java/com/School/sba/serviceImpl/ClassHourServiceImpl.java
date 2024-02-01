package com.School.sba.serviceImpl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.time.Duration;

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
	
	
	private boolean isBreakTime(LocalDateTime beginsAt, LocalDateTime endsAt, Schedule schedule) {
		LocalTime breakTimeStart = schedule.getBreakTime();

		return ((breakTimeStart.isAfter(beginsAt.toLocalTime()) && breakTimeStart.isBefore(endsAt.toLocalTime()))
				|| breakTimeStart.equals(beginsAt.toLocalTime()));
	}

	private boolean isLunchTime(LocalDateTime beginsAt, LocalDateTime endsAt, Schedule schedule) {
		LocalTime lunchTimeStart = schedule.getLunchTime();

		return ((lunchTimeStart.isAfter(beginsAt.toLocalTime()) && lunchTimeStart.isBefore(endsAt.toLocalTime()))
				|| lunchTimeStart.equals(beginsAt.toLocalTime()));
	}


	@Override
	public ResponseEntity<ResponseStructure<String>> genereateClassHoursForAcademicProgram(int programId) {

			return academicProgramRepository.findById(programId).map(academicProgarm -> {
				School school = academicProgarm.getSchool();
				Schedule schedule = school.getSchedule();
				if (schedule != null) {
					int classHourPerDay = schedule.getClassHoursPerDay();
					int classHourLength = (int) schedule.getClassHourLengthInMinute().toMinutes();

					LocalDateTime currentTime = LocalDateTime.now().with(schedule.getOpensAt());

					LocalDateTime lunchTimeStart = LocalDateTime.now().with(schedule.getLunchTime());
					LocalDateTime lunchTimeEnd = lunchTimeStart.plusMinutes(schedule.getLunchLengthInMinute().toMinutes());
					LocalDateTime breakTimeStart = LocalDateTime.now().with(schedule.getBreakTime());
					LocalDateTime breakTimeEnd = breakTimeStart.plusMinutes(schedule.getBreakLengthInMinute().toMinutes());

					for (int day = 1; day <= 6; day++) {
						for (int hour = 1; hour <= classHourPerDay + 2; hour++) {
							ClassHour classHour = new ClassHour();
							LocalDateTime beginsAt = currentTime;
							LocalDateTime endsAt = beginsAt.plusMinutes(classHourLength);

							if (!isLunchTime(beginsAt, endsAt, schedule)) {
								if (!isBreakTime(beginsAt, endsAt, schedule)) {
									classHour.setBeginsAt(beginsAt);
									classHour.setEndsAt(endsAt);
									classHour.setClassStatus(ClassStatus.NOT_SCHEDULED);

									currentTime = endsAt;
								} else {
									classHour.setBeginsAt(breakTimeStart);
									classHour.setEndsAt(breakTimeEnd);
									classHour.setClassStatus(ClassStatus.BREAK_TIME);
									currentTime = breakTimeEnd;
								}
							} else {
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

				} else
					throw new UserNotFoundException(
							"The school does not contain any schedule, please provide a schedule to the school");

				responseStructure.setData("ClassHour generated successfully for the academic progarm");
				responseStructure.setMessage("Class Hour generated for the current week successfully");
				responseStructure.setStatusCode(HttpStatus.CREATED.value());
				return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.CREATED);
				
			}).orElseThrow(() -> new UserNotFoundException("Invalid Program Id"));
		
		
			}



	

	@Override
	public Object updateClassHoursForAcademicProgram(List<ClassHourRequestDTO> classHourRequestDTO) {
		classHourRequestDTO.forEach((req) -> {
					int userId = req.getUserId();
					User user = userRepository.findById(userId)
							.orElseThrow(() -> new UserNotFoundException("User with given ID is not registered in the database"));
					int roomNo = req.getRoomNo();
					int hourId = req.getClassHourId();
					ClassHour classHour = classHourRepository.findById(hourId).orElseThrow(
							() -> new UserNotFoundException("ClassHour with given ID is not registered in the database"));
					int subjectId = req.getSubjectId();
					Subject subject = subjectRepository.findById(subjectId).orElseThrow(
							() -> new UserNotFoundException("Subject with given ID is not registered in the database"));
					if (!classHourRepository.existsByRoomNoAndBeginsAtBetween(roomNo, classHour.getBeginsAt().minusMinutes(1),
							classHour.getEndsAt().plusMinutes(1))) {
						if (user.getUserRole().equals(UserRole.TEACHER)) {
							classHour.setRoomNo(roomNo);
							classHour.setSubject(subject);
							classHour.setUser(user);
							classHourRepository.save(classHour);
						} else {
							throw new ConstraintVoilationException("Invalid User Id");
						}
					} else {
						throw new UserNotFoundException("Class Hour already contains Room No");
					}
				});
				return "ClassHour updated";
			
	}	

		
	//=======================================================================================================================================
				
//	public ResponseEntity<ResponseStructure<ClassHourResponseDTO>> deleteClassHour(List<ClassHour> classHours)
//	{
//		int scheduleId=schedule.getScheduleId();
//	scheduleRepository.findById(scheduleId).orElseThrow(() -> new  ScheduleNotFoundException("Schedule Does not exist"));
//		
//		return null;
//	}	
			

	

}
