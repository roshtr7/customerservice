package com.org.haud.customerservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.org.haud.customerservice.entity.SimCard;

public interface SimCardRepository extends JpaRepository<SimCard, Long> {

	@Query("SELECT s FROM SimCard s WHERE s.ICCID = (:iccid) OR s.IMSI = (:imsi)")
	Optional<SimCard> findByIccidORImsi(String iccid, String imsi);

}
