package com.org.haud.customerservice.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimCardDto {

	private Long Id;

	@NotBlank(message = "ICCID name is mandatory")
	private String ICCID;

	@NotBlank(message = "IMSI is mandatory")
	private String IMSI;

}