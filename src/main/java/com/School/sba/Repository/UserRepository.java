package com.School.sba.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.School.sba.Enum.UserRole;
import com.School.sba.entity.AcademicProgram;
import com.School.sba.entity.School;
import com.School.sba.entity.User;


public interface UserRepository extends JpaRepository<User, Integer> {


	List<User> findByUserRoleAndAcademicProgramList(AcademicProgram program, UserRole role);


	List<User> findBySchool(School school);

	List<User> findByIsDeleted(boolean b);

	

	

}
