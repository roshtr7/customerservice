package com.org.haud.customerservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.haud.customerservice.dto.ResponseDto;
import com.org.haud.customerservice.dto.SimCardDto;
import com.org.haud.customerservice.service.SimCardService;

@RestController
@RequestMapping("/sim")
public class SimController {

	@Autowired
	private SimCardService simCardService;

	public ResponseEntity<ResponseDto> createSim(SimCardDto simDto) {
		ResponseDto responseDto = simCardService.createSimCard(simDto);
		if (responseDto.getErrors() != null) {
			return ResponseEntity.badRequest().body(responseDto);
		}
		return ResponseEntity.ok(responseDto);
	}

	// Not recommended in production. Need to implement pagination.
	public ResponseEntity<ResponseDto> getAllSims() {
		List<SimCardDto> simCardList = simCardService.findAll();
		return ResponseEntity.ok(ResponseDto.builder().data(simCardList).build());
	}

}
