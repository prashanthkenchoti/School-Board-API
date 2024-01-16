package com.School.sba.utility;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
@OpenAPIDefinition
public class ApplicationDocumentation {
	
	Contact contact() {
		return new Contact().email("he@gmail.com")
				.url("ach.fj.in");
				
				
	}
	
	private License  license() {
		
		return new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");
		

	}
	Info info()
	{
		return new Info().title("School Management API")
				.version("1.0v")
				.termsOfService("once you buy our product, we will Never Gonna take it back")
				.description("Schol Board API is a RESTful API built Using"+
				"SpringBoot and MySql Database")
				.contact(new Contact());
				//.license(license());
		
	}
	
	
	@Bean
	OpenAPI openAPI()
	{
		return new OpenAPI().info(info());
	}
	
	 ModelMapper  getModelMapper() {
		return new ModelMapper();

	}

}
