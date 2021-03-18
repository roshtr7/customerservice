package com.org.haud.customerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.haud.customerservice.dto.CustomerDto;
import com.org.haud.customerservice.dto.ResponseDto;
import com.org.haud.customerservice.exception.CustomerServiceException;
import com.org.haud.customerservice.scheduler.EmailSchedulerService;
import com.org.haud.customerservice.service.CustomerService;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private EmailSchedulerService emailScheduler;

	@PostMapping
	public ResponseEntity<ResponseDto> createCustomer(@RequestBody CustomerDto customerDto)
			throws CustomerServiceException {
		ResponseDto responseDto = customerService.createUser(customerDto);
		if (responseDto.getErrors() != null) {
			return ResponseEntity.badRequest().body(responseDto);
		}
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/sim")
	public ResponseEntity<ResponseDto> retrieveCustomerSims(@RequestParam Long customerId) {
		return ResponseEntity
				.ok(ResponseDto.builder().data(customerService.findAllSimsByCustomerId(customerId)).build());
	}

	@GetMapping("/testemail")
	public void testEmail() {
		emailScheduler.sendBirthdayEmail();
	}
//
//	@GetMapping("/export")
//	public void testEmailExport() {
//		emailScheduler.exportCustomers();
//	}

	@PostMapping("/link-sim")
	public ResponseEntity<ResponseDto> linkCustomerAndSim(@RequestParam Long customerId, @RequestParam Long simId)
			throws CustomerServiceException {
		customerService.linkSim(customerId, simId);
		return ResponseEntity.ok(ResponseDto.builder().build());
	}

}
