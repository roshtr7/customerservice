package com.org.haud.customerservice.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.org.haud.customerservice.entity.Customer;
import com.org.haud.customerservice.util.AppConstants;

@Service("emailService")
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;

	@Value("${haud.email.from}")
	private String from;

	@Value("${haud.email.template.bday}")
	private String bdayTemplate;

	@Autowired
	private TemplateEngine templateEngine;

	public void sendEmail(Customer customer) throws MessagingException {
		// Prepare the evaluation context
		final Context ctx = new Context();
		ctx.setVariable("name", customer.getFirstName());
		String html = templateEngine.process("bday", ctx);
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
		message.setFrom(from);
		message.setTo(customer.getEmail());
		message.setSubject(AppConstants.Emails.ADVANCEHAPPYBDAY);
		message.setText(html, true);
		emailSender.send(mimeMessage);
	}
}
