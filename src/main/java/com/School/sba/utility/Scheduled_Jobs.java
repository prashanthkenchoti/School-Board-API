package com.School.sba.utility;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.School.sba.Repository.AcademicProgramRepository;
import com.School.sba.entity.AcademicProgram;
import com.School.sba.service.ClassHourService;
import com.School.sba.service.SchoolService;
import com.School.sba.service.UserService;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class Scheduled_Jobs {

	@Autowired
	UserService userService;

	@Autowired
	SchoolService schoolService;

	@Autowired
	ClassHourService classHourService;

	@Autowired
	AcademicProgramRepository academicProgramRepository;


	@Scheduled(fixedDelay = 100000l)// fixed delay accepts values in mili seconds (datatype is long so add l at the end) 
	public void delete()
	{


		String deleteuser = userService.deleteuser();
		log.info(deleteuser);

		log.info(schoolService.deleteSchool());


	}
	//<MIn> <Hour> <day Of Month> <month> <day Of Week>
	@Scheduled(cron  = "0 0 0 * *  MON")
	public void generateClassHours() {

		List<AcademicProgram> findAll = academicProgramRepository.findAll();
		findAll.forEach((ac)-> {
			if(ac.isAutoRepeat()) {
				classHourService.craeteClassHoursForNextWeek(ac.getProgramId());
			}
		});

	}



}
