package com.org.haud.customerservice.scheduler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;
import com.org.haud.customerservice.entity.Customer;
import com.org.haud.customerservice.entity.SimCard;
import com.org.haud.customerservice.service.CustomerService;
import com.org.haud.customerservice.service.EmailService;

import net.javacrumbs.shedlock.core.SchedulerLock;

@Service
public class EmailSchedulerServiceImpl implements EmailSchedulerService {

	private static final Logger logger = LogManager.getLogger(EmailSchedulerServiceImpl.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	private EmailService emailService;

	@Value("${haud.csv}")
	private String filePath;

	@Value("${haud.companyemail}")
	private String companyEmail;

	@Override
	@Scheduled(cron = "0 0 0 * * ?")
	@SchedulerLock(name = "birthday_email_cron", lockAtLeastForString = "PT10M", lockAtMostForString = "PT15M")
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

	@Override
	@Scheduled(cron = "0 0 0 * * ?")
	@SchedulerLock(name = "customer_export_cron", lockAtLeastForString = "PT10M", lockAtMostForString = "PT15M")
	public void exportCustomers() {
		List<Customer> customerList = customerService.getAllCustomerHavingBday();
		writeDataLineByLine(filePath, customerList);

	}

	public void writeDataLineByLine(String filePath, List<Customer> customerList) {
		if (!customerList.isEmpty()) {
			UUID uuid = UUID.randomUUID();
			File file = new File(filePath + "/" + uuid + ".csv");
			try {

				FileWriter outputfile = new FileWriter(file);

				CSVWriter writer = new CSVWriter(outputfile);

				// adding header to csv
				String[] header = { "Name", "Email", "Sim" };
				writer.writeNext(header);
				customerList.stream().forEach(c -> {
					Set<SimCard> simCards = c.getSimCards();
					String sim = simCards.stream().map(SimCard::getIMSI).collect(Collectors.joining(", "));
					String[] rows = { c.getFirstName() + " " + c.getLastName(), c.getEmail(), sim };
					writer.writeNext(rows);
				});

				// closing writer connection
				writer.close();
				emailService.sendMailWithAttachment(companyEmail, "Customer Export", "PFA", file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
