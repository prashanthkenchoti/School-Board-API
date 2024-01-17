package com.School.sba.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.School.sba.entity.AcademicProgram;
@Repository
public interface AcademicProgramRepository extends JpaRepository<AcademicProgram, Integer> {

}
