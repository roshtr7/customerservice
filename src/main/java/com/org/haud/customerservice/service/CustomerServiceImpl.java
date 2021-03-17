package com.org.haud.customerservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.haud.customerservice.dto.CustomerDto;
import com.org.haud.customerservice.dto.ResponseDto;
import com.org.haud.customerservice.entity.Customer;
import com.org.haud.customerservice.entity.SimCard;
import com.org.haud.customerservice.exception.CustomerServiceException;
import com.org.haud.customerservice.repository.CustomerRepository;
import com.org.haud.customerservice.util.AppConstants;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public ResponseDto createUser(CustomerDto userDto) throws CustomerServiceException {
		List<String> errorList = validateUser(userDto);
		if (errorList.size() > 0) {
			return ResponseDto.builder().errors(errorList).build();
		}
		validateEmail(userDto.getEmail());
		Customer customer = Customer.builder().firstName(userDto.getFirstName()).lastName(userDto.getLastName())
				.email(userDto.getEmail()).dateOfBirth(userDto.getDateOfBirth()).build();
		customerRepository.save(customer);
		return ResponseDto.builder().build();
	}
	
	public List<String> validateUser(CustomerDto user) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<CustomerDto>> violations = validator.validate(user);
		List<String> errorList = new ArrayList<>();
		for (ConstraintViolation<CustomerDto> violation : violations) {
			errorList.add(violation.getMessage());
		}
		return errorList;
	}
	
	public void validateEmail(String email) throws CustomerServiceException {
		Optional<Customer> userId = customerRepository.findByEmail(email);
		userId.ifPresent(u -> {new CustomerServiceException(AppConstants.ErrorMsgs.DUPLICATE_EMAIL);});
	}
	
	@Override
	public List<SimCard> findAllSimsByCustomerId(Long customerId) {
		return Optional.ofNullable(customerRepository.findSimsByCustomerId(customerId)).orElse(new ArrayList<>());
	}

}
