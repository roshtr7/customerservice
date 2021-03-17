package com.org.haud.customerservice.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDto {

	private Long id;

	@NotBlank(message = "First name is mandatory")
	private String firstName;

	@NotBlank(message = "Last name is mandatory")
	private String lastName;

	@NotBlank(message = "Email is mandatory")
	private String email;

	@NotBlank(message = "Date of birth is mandatory")
	private Date dateOfBirth;
	
}