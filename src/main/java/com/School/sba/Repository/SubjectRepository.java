package com.School.sba.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.School.sba.entity.Subject;
import com.School.sba.requestdto.SubjectRequestDTO;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
	
	@Query("select s from Subject s where s.subjectName LIKE %?1%")
	Subject findSubjectByName(String subjectRequestDTO);

}
