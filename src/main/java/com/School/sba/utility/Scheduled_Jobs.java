package com.School.sba.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
	
	@Scheduled(fixedDelay = 1000l)// fixed delay accepts values in mili seconds (datatype is long so add l at the end) 
	public void test()
	{
		
		
		String deleteuser = userService.deleteuser();
		log.info(deleteuser);
		
		log.info(schoolService.deleteSchool());
		
		
	}
	


}
