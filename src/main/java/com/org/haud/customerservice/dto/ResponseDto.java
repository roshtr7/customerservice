package com.org.haud.customerservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {

	Object data;

	Object errors;

}
