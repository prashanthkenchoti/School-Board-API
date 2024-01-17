package com.School.sba.entity;

import com.School.sba.Enum.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	@Column(unique = true)
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private long contactNo;
	@Column(unique = true)
	private String email;
	private UserRole userRole;
	private boolean isDeleted=false;
	

	private School school; 
}
