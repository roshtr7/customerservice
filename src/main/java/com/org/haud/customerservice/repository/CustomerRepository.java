package com.org.haud.customerservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.org.haud.customerservice.entity.Customer;
import com.org.haud.customerservice.entity.SimCard;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	@Query("SELECT c FROM Customer c where c.email = (:email)")
	Optional<Customer> findByEmail(@Param("email")String email);
	
	@Query("SELECT c.simCardList FROM Customer c WHERE c.id = (:id)")
	List<SimCard> findSimsByCustomerId(Long id);

}
