package com.org.haud.customerservice.service;

import java.io.File;

import javax.mail.MessagingException;

import com.org.haud.customerservice.entity.Customer;

public interface EmailService {

	void sendEmail(Customer customer) throws MessagingException;

	void sendMailWithAttachment(String to, String subject, String body, File fileToAttach);

}