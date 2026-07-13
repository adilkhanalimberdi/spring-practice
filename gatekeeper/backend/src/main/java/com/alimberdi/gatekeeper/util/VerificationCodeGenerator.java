package com.alimberdi.gatekeeper.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class VerificationCodeGenerator {

	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final SecureRandom random = new SecureRandom();

	public String generate() {
		StringBuilder builder = new StringBuilder();

		while (builder.length() < 6) {
			int index = random.nextInt(ALPHABET.length());
			builder.append(ALPHABET.charAt(index));
		}

		return builder.toString();
	}

}
