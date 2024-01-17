package com.School.sba.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class School {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int schoolId;
	private String schoolName;
	private Long contactNo;
	private String emailId;
	private  String address;
	
	//@OneToOne(mappedBy = "school")
	//private Schedule schedule;
	
	@ManyToOne
	private List<User> userlist;
	
	@OneToOne
	private Schedule schedule;
	
	
	

}
