package com.School.sba.serviceImpl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
import com.School.sba.entity.AcademicProgram;
import com.School.sba.entity.ClassHour;
import com.School.sba.entity.Schedule;
import com.School.sba.entity.School;
import com.School.sba.entity.Subject;
import com.School.sba.entity.User;
import com.School.sba.requestdto.ClassHourRequestDTO;
import com.School.sba.requestdto.ExcelRequestDTO;
import com.School.sba.responsedto.ClassHourResponseDTO;
import com.School.sba.responsedto.ScheduleResponseDTO;
import com.School.sba.service.ClassHourService;
import com.School.sba.utility.ResponseStructure;

@Service
public class ClassHourServiceImpl implements ClassHourService {

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

	@Autowired
	ResponseStructure<List<ClassHour>> hourStructure;

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
				LocalDateTime nextSaturday = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
						.plusDays(7);

				while (currentTime.isBefore(nextSaturday) && currentTime.getDayOfWeek() != DayOfWeek.SUNDAY) {
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
				}

			} else
				throw new ScheduleNotFoundException(
						"The school does not contain any schedule, please provide a schedule to the school");

			responseStructure.setData("ClassHour generated successfully for the academic progarm");
			responseStructure.setMessage("Class Hour generated for the current week successfully");
			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.CREATED);

		}).orElseThrow(() -> new UserNotFoundException("Invalid Program Id"));

	}

	// ================================================================================================

	@Override
	public String updateClassHoursForAcademicProgram(List<ClassHourRequestDTO> classHourRequestDTO) {
		classHourRequestDTO.forEach((req) -> {
			int userId = req.getUserId();
			User user = userRepository.findById(userId).orElseThrow(
					() -> new UserNotFoundException("User with given ID is not registered in the database"));
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

	@Override
	public ResponseEntity<ResponseStructure<List<ClassHour>>> craeteClassHoursForNextWeek(int programId) {

		AcademicProgram academicProgram = academicProgramRepository.findById(programId).get();
		List<ClassHour> classHours = academicProgram.getClassHour();
		classHours.forEach((cl) -> {
			// createNewClassHour(ClassHour classHour) is down side we created
			ClassHour createNewClassHour = createNewClassHour(cl);
			classHours.add(createNewClassHour);
		});

		classHours.forEach((hour) -> {
			LocalDateTime plusDays = hour.getBeginsAt().plusDays(7);
			hour.setBeginsAt(plusDays);
			classHourRepository.save(hour);
		});
		hourStructure.setMessage("New Class Hour Created For Next Week");
		hourStructure.setStatusCode(HttpStatus.CREATED.value());
		hourStructure.setData(classHours);
		return new ResponseEntity<ResponseStructure<List<ClassHour>>>(hourStructure, HttpStatus.CREATED);

	}

	private ClassHour createNewClassHour(ClassHour cl) {
		ClassHour classHour2 = new ClassHour();

		classHour2.setAcademicProgram(cl.getAcademicProgram());
		classHour2.setBeginsAt(cl.getBeginsAt());
		classHour2.setClassStatus(cl.getClassStatus());
		classHour2.setEndsAt(cl.getEndsAt());
		classHour2.setRoomNo(cl.getRoomNo());
		classHour2.setSubject(cl.getSubject());
		classHour2.setUser(cl.getUser());

		return classHour2;
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> printData(int programId, ExcelRequestDTO excelRequestDTO) {
		AcademicProgram program = academicProgramRepository.findById(programId)
				.orElseThrow(() -> new AcademicProgramNotFoundException("Academic program Not Found"));
		String folderPath = "C:\\Users\\gp\\Downloads\\Demo";
		String filepath = folderPath + "\\test.xlsx";

		LocalDateTime from = excelRequestDTO.getFromDate().atTime(LocalTime.MIDNIGHT);
		LocalDateTime to = excelRequestDTO.getToDate().atTime(LocalTime.MIDNIGHT);
		List<ClassHour> classHours = classHourRepository.findByAcademicProgramAndBeginsAtBetween(program, from, to);

		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet();

		int rowNummber = 0;
		Row header = sheet.createRow(rowNummber);
		header.createCell(0).setCellValue("Date");
		header.createCell(1).setCellValue("from");
		header.createCell(2).setCellValue("to");
		header.createCell(3).setCellValue("Subject");
		header.createCell(4).setCellValue("Teacher");
		header.createCell(5).setCellValue("RoomNo");

		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		for (ClassHour classhour : classHours) {

			Row row = sheet.createRow(++rowNummber);
			row.createCell(0).setCellValue(dateFormatter.format(classhour.getBeginsAt()));
			row.createCell(1).setCellValue(dateFormatter.format(classhour.getBeginsAt()));
			row.createCell(2).setCellValue(dateFormatter.format(classhour.getEndsAt()));
			if (classhour.getSubject() == null) {
				row.createCell(3).setCellValue("");
			} else {
				row.createCell(3).setCellValue(classhour.getSubject().getSubjectName());
			}
			if (classhour.getUser() == null) {
				row.createCell(4).setCellValue("");
			} else {
				row.createCell(4).setCellValue(classhour.getUser().getUserName());
			}

			row.createCell(5).setCellValue(classhour.getRoomNo());

		}
		try {
			workbook.write(new FileOutputStream(filepath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("data is ready to print");
		responseStructure.setData("data printed to excel sheet successfully");
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.OK);

	}

	//writing into exel fle in web appication

	@Override
	public ResponseEntity<?> writeTOExcel(MultipartFile file, int programId, LocalDate fromDate, LocalDate toDate) throws IOException {
		// No need to create cell and row bcoz they already exist in this method
		AcademicProgram program = academicProgramRepository.findById(programId)
				.orElseThrow(() -> new AcademicProgramNotFoundException("Academic program Not Found"));
		List<ClassHour> classHours = classHourRepository.findByAcademicProgramAndBeginsAtBetween(program, fromDate,
				toDate);
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		XSSFWorkbook workbook;
		workbook = new XSSFWorkbook(file.getInputStream());
		workbook.forEach(sheet -> {

			int rowNummber = 0;
			Row header = sheet.createRow(rowNummber);
			header.createCell(0).setCellValue("Date");
			header.createCell(1).setCellValue("from");
			header.createCell(2).setCellValue("to");
			header.createCell(3).setCellValue("Subject");
			header.createCell(4).setCellValue("Teacher");
			header.createCell(5).setCellValue("RoomNo");

			for (ClassHour classhour : classHours) {

				Row row = sheet.createRow(++rowNummber);
				row.createCell(0).setCellValue(dateFormatter.format(classhour.getBeginsAt()));
				row.createCell(1).setCellValue(dateFormatter.format(classhour.getBeginsAt()));
				row.createCell(2).setCellValue(dateFormatter.format(classhour.getEndsAt()));
				if (classhour.getSubject() == null) {
					row.createCell(3).setCellValue("");
				} else {
					row.createCell(3).setCellValue(classhour.getSubject().getSubjectName());
				}
				if (classhour.getUser() == null) {
					row.createCell(4).setCellValue("");
				} else {
					row.createCell(4).setCellValue(classhour.getUser().getUserName());
				}

				row.createCell(5).setCellValue(classhour.getRoomNo());

			}

		});
		// to write in preexisting file, 
		//outPutStream is just a byte stream , it will not write anything
		ByteArrayOutputStream outPutStream=new ByteArrayOutputStream();
		workbook.write(outPutStream);
		
		byte[] byteData= outPutStream.toByteArray();

		return ResponseEntity.ok()
				.header("Content Disposition", "attachement:filename="+file.getOriginalFilename())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(byteData);
	}

}
