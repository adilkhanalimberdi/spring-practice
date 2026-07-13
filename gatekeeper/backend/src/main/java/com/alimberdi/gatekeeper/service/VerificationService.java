package com.alimberdi.gatekeeper.service;

import com.alimberdi.gatekeeper.exception.ResourceNotFoundException;
import com.alimberdi.gatekeeper.util.VerificationCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class VerificationService {

	private final EmailService emailService;
	private final StringRedisTemplate redisTemplate;
	private final VerificationCodeGenerator codeGenerator;

	private static final String VERIFICATION_KEY_PREFIX = "verification:email:";

	public void sendVerificationCode(String email) {
		String code = codeGenerator.generate();
		emailService.sendVerification(email, code);
		redisTemplate.opsForValue().set(VERIFICATION_KEY_PREFIX + email, code, Duration.ofMinutes(15));
	}

	public String getVerificationCode(String email) {
		String value = redisTemplate.opsForValue().get(VERIFICATION_KEY_PREFIX + email);

		if (value == null) {
			throw new ResourceNotFoundException("Verification code expired or not found");
		}

		return value;
	}

	public void removeVerificationCode(String email) {
		redisTemplate.delete(VERIFICATION_KEY_PREFIX + email);
	}

}
