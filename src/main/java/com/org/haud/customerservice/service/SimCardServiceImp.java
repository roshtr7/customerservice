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

import com.org.haud.customerservice.dto.ResponseDto;
import com.org.haud.customerservice.dto.SimCardDto;
import com.org.haud.customerservice.entity.SimCard;
import com.org.haud.customerservice.exception.CustomerServiceException;
import com.org.haud.customerservice.repository.SimCardRepository;
import com.org.haud.customerservice.util.AppConstants;

@Service
public class SimCardServiceImp implements SimCardService {

	@Autowired
	private SimCardRepository simCardRepository;

	@Override
	public ResponseDto createSimCard(SimCardDto simDto) throws CustomerServiceException {
		List<String> errorList = validateSimCardDto(simDto);
		if (errorList.size() > 0) {
			return ResponseDto.builder().errors(errorList).build();
		}
		validateSimExists(simDto);
		SimCard simCard = SimCard.builder().ICCID(simDto.getICCID()).IMSI(simDto.getIMSI()).build();
		simCardRepository.save(simCard);
		return ResponseDto.builder().build();

	}

	public List<String> validateSimCardDto(SimCardDto simDto) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<SimCardDto>> violations = validator.validate(simDto);
		List<String> errorList = new ArrayList<>();
		for (ConstraintViolation<SimCardDto> violation : violations) {
			errorList.add(violation.getMessage());
		}
		return errorList;
	}

	@Override
	public List<SimCardDto> findAll() {
		List<SimCard> simCardList = simCardRepository.findAll();
		List<SimCardDto> simDtoList = new ArrayList<>();
		simCardList.stream().forEach(s -> {
			SimCardDto simDto = SimCardDto.builder().ICCID(s.getICCID()).IMSI(s.getIMSI()).Id(s.getId()).build();
			simDtoList.add(simDto);
		});
		return simDtoList;
	}

	public void validateSimExists(SimCardDto simDto) throws CustomerServiceException {
		Optional<SimCard> sim = simCardRepository.findByIccidORImsi(simDto.getICCID(), simDto.getIMSI());
		if (sim.isPresent()) {
			throw new CustomerServiceException(AppConstants.ErrorMsgs.DUPLICATE_SIM);
		}
	}

}
