package com.org.haud.customerservice.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimCardDto {

	private Long Id;

	@NotBlank(message = "ICCID name is mandatory")
	private String ICCID;

	@NotBlank(message = "IMSI is mandatory")
	private String IMSI;

}