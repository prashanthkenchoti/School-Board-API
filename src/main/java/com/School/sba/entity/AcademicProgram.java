package com.School.sba.entity;

import java.util.List;

import org.hibernate.annotations.GeneratorType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcademicProgram {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int programId;
	private String programName;
	private int beginsAt;
	private int endsAt;
	
	@ManyToOne
	private School school;
	
	@ManyToMany
	private List<User> UserList;

}
