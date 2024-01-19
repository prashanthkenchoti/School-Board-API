package com.School.sba.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.School.sba.Enum.UserRole;
import com.School.sba.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	
	
	//void findUserRole(UserRole userRole);

}
