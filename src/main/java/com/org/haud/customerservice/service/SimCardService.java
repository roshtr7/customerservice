package com.org.haud.customerservice.service;

import java.util.List;

import com.org.haud.customerservice.dto.ResponseDto;
import com.org.haud.customerservice.dto.SimCardDto;

public interface SimCardService {

	ResponseDto createSimCard(SimCardDto simDto);

	List<SimCardDto> findAll();

}
