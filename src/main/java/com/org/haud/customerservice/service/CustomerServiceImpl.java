package com.org.haud.customerservice.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.haud.customerservice.dto.CustomerDto;
import com.org.haud.customerservice.dto.ResponseDto;
import com.org.haud.customerservice.dto.SimCardDto;
import com.org.haud.customerservice.entity.Customer;
import com.org.haud.customerservice.entity.SimCard;
import com.org.haud.customerservice.exception.CustomerServiceException;
import com.org.haud.customerservice.repository.CustomerRepository;
import com.org.haud.customerservice.repository.SimCardRepository;
import com.org.haud.customerservice.util.AppConstants;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private SimCardRepository simCardRepository;

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
		Optional<Customer> customer = customerRepository.findByEmail(email);
		if (customer.isPresent()) {
			throw new CustomerServiceException(AppConstants.ErrorMsgs.DUPLICATE_EMAIL);
		}
	}

	@Override
	public Set<SimCardDto> findAllSimsByCustomerId(Long customerId) {
		Set<SimCard> simCards = Optional.ofNullable(customerRepository.findSimsByCustomerId(customerId))
				.orElse(new HashSet<>());
		Set<SimCardDto> simDtoList = mapSimCardsToSimCardDtos(simCards);
		return simDtoList;
	}

	private Set<SimCardDto> mapSimCardsToSimCardDtos(Set<SimCard> simCards) {
		Set<SimCardDto> simDtoList = new HashSet<>();
		simCards.stream().forEach(s -> {
			SimCardDto simDto = SimCardDto.builder().Id(s.getId()).ICCID(s.getICCID()).IMSI(s.getIMSI()).build();
			simDtoList.add(simDto);
		});
		return simDtoList;
	}

	@Override
	public List<Customer> getAllCustomerHavingBday() {
		return Optional.ofNullable(customerRepository.findAllCustomerHavingBday()).orElse(new ArrayList<>());
	}

	@Override
	public List<Customer> getAllCustomerHavingBdayAfter7Days() {
		return Optional.ofNullable(customerRepository.findAllCustomerHavingBdayAfter7Days()).orElse(new ArrayList<>());
	}

	@Override
	@Transactional
	public void linkSim(Long customerId, Long simId) throws CustomerServiceException {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerServiceException(AppConstants.ErrorMsgs.CUSTOMER_NOT_FOUND));
		SimCard sim = simCardRepository.findById(simId)
				.orElseThrow(() -> new CustomerServiceException(AppConstants.ErrorMsgs.SIM_NOT_FOUND));
		customer.addSimCard(sim);
		customerRepository.save(customer);
	}

}
