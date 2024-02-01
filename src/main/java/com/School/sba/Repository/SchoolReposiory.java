package com.School.sba.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.School.sba.entity.School;

public interface SchoolReposiory extends JpaRepository<School, Integer> {

	@Query("select s from School s where s.schoolName LIKE %?1%")
	List<School> findSchoolByName(String schoolName);

	List<School> findByIsDeleted(boolean b);



}
