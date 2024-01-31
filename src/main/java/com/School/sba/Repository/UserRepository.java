package com.School.sba.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.School.sba.Enum.UserRole;
import com.School.sba.entity.AcademicProgram;
import com.School.sba.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findByUserRoleAndAcademicPrograms(AcademicProgram program, List<User> role);

	List<User> findByUserRoleAndAcademicPrograms(AcademicProgram program, UserRole role);

	
	
	//void findUserRole(UserRole userRole);

}
