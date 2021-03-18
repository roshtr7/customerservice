package com.org.haud.customerservice.service;

import java.util.List;
import java.util.Set;

import com.org.haud.customerservice.dto.CustomerDto;
import com.org.haud.customerservice.dto.ResponseDto;
import com.org.haud.customerservice.dto.SimCardDto;
import com.org.haud.customerservice.entity.Customer;
import com.org.haud.customerservice.exception.CustomerServiceException;

public interface CustomerService {

	ResponseDto createUser(CustomerDto userDto) throws CustomerServiceException;

	Set<SimCardDto> findAllSimsByCustomerId(Long customerId);

	List<Customer> getAllCustomerHavingBday();

	List<Customer> getAllCustomerHavingBdayAfter7Days();

	void linkSim(Long customerId, Long simId) throws CustomerServiceException;

}
