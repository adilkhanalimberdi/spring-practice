package com.alimberdi.gatekeeper.exception;

public class VerificationFailedException extends RuntimeException {
	public VerificationFailedException(String message) {
		super(message);
	}
}
