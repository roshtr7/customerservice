package com.org.haud.customerservice.exception;

public class CustomerServiceException extends Exception {

	private static final long serialVersionUID = -5070712613798120345L;

	public CustomerServiceException(String message) {
		super(message);
	}

	public CustomerServiceException(String message, Exception ex) {
		super(message, ex);
	}

}
