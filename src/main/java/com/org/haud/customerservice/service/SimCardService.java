package com.org.haud.customerservice.service;

import java.util.List;

import com.org.haud.customerservice.dto.ResponseDto;
import com.org.haud.customerservice.dto.SimCardDto;
import com.org.haud.customerservice.exception.CustomerServiceException;

public interface SimCardService {

	ResponseDto createSimCard(SimCardDto simDto) throws CustomerServiceException;

	List<SimCardDto> findAll();

}
