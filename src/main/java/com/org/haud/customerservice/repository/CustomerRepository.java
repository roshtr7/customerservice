package com.org.haud.customerservice.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.org.haud.customerservice.entity.Customer;
import com.org.haud.customerservice.entity.SimCard;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	@Query("SELECT c FROM Customer c where c.email = (:email)")
	Optional<Customer> findByEmail(@Param("email") String email);

	@Query("SELECT c.simCards FROM Customer c WHERE c.id = (:id)")
	Set<SimCard> findSimsByCustomerId(Long id);

	@Query("SELECT c FROM Customer c WHERE DATE_FORMAT(c.dateOfBirth,'%m-%d') = DATE_FORMAT(NOW(),'%m-%d')")
	List<Customer> findAllCustomerHavingBday();

//	@Query("SELECT c FROM Customer c WHERE DATE_FORMAT(c.dateOfBirth,'%m-%d') = DATE_FORMAT(ADDDATE(NOW(),+7),'%m-%d')")
//	List<Customer> findAllCustomerHavingBdayAfter7Days();

	@Query("SELECT c FROM Customer c WHERE DATE_FORMAT(c.dateOfBirth,'%m-%d') = DATE_FORMAT((:date),'%m-%d')")
	List<Customer> findAllCustomerByDateOfBirth(Date date);
}
