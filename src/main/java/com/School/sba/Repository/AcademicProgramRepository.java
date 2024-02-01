package com.School.sba.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.School.sba.Enum.UserRole;
import com.School.sba.entity.AcademicProgram;
import com.School.sba.entity.User;

public interface AcademicProgramRepository extends JpaRepository<AcademicProgram, Integer> {


	

	


}
