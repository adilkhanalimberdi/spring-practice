package com.alimberdi.gatekeeper.exception;

public class EmailMessagingFailedException extends RuntimeException {
	public EmailMessagingFailedException(String message) {
		super(message);
	}
}
