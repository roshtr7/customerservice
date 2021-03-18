package com.org.haud.customerservice.scheduler;

import java.util.List;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.org.haud.customerservice.entity.Customer;
import com.org.haud.customerservice.service.CustomerService;
import com.org.haud.customerservice.service.EmailService;

import net.javacrumbs.shedlock.core.SchedulerLock;

@Service
public class EmailSchedulerServiceImpl implements EmailSchedulerService {

	private static final Logger logger = LogManager.getLogger(EmailSchedulerServiceImpl.class);

	@Autowired
	CustomerService customerService;

	@Autowired
	EmailService emailService;

	@Scheduled(cron = "0 0 0 * * ?")
	@SchedulerLock(name = "birthday_email", lockAtLeastForString = "PT10M", lockAtMostForString = "PT15M")
	public void sendBirthdayEmail() {
		logger.info("Sending bday notification email!");
		List<Customer> customerList = customerService.getAllCustomerHavingBdayAfter7Days();
		customerList.stream().forEach(c -> {
			try {
				emailService.sendEmail(c);
			} catch (MessagingException e) {
				logger.error("Exception occured: ", e);
			}
		});
	}

}
