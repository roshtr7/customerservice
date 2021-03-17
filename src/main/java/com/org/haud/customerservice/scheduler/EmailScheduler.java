package com.org.haud.customerservice.scheduler;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.org.haud.customerservice.entity.Customer;
import com.org.haud.customerservice.service.CustomerService;
import com.org.haud.customerservice.service.EmailService;

import net.javacrumbs.shedlock.core.SchedulerLock;

@Service
public class EmailScheduler {

	@Autowired
	CustomerService customerService;

	@Autowired
	EmailService emailService;

	@Scheduled(cron = "0 0 0 * * ?")
	@SchedulerLock(name = "birthday_email", lockAtLeastForString = "PT30M", lockAtMostForString = "PT50M")
	public void sendBirthdayEmail() {
		List<Customer> customerList = customerService.getAllCustomerHavingBdayAfter7Days();
		customerList.stream().forEach(c -> {
			try {
				emailService.sendEmail(c);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		});
	}

}
