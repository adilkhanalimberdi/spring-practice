package com.alimberdi.gatekeeper.service;

import com.alimberdi.gatekeeper.exception.EmailMessagingFailedException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender mailSender;
	private final org.thymeleaf.TemplateEngine templateEngine;

	@Async
	public void sendVerification(String to, String code) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			Context context = new Context();
			context.setVariable("code", code);
			String htmlContent = templateEngine.process("verification-email", context);

			helper.setTo(to);
			helper.setSubject("Gatekeeper - Verification code");
			helper.setText(htmlContent, true);
			helper.setFrom("gatekeeper@alimberdi.com");

			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new EmailMessagingFailedException("Failed to send verification code");
		}
	}

}
