package com.School.sba.requestdto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExcelRequestDTO {
	
	private LocalDate fromDate;
	private LocalDate toDate;
	private String filePath; 
	

}
