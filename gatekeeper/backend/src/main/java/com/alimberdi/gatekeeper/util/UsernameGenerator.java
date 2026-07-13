package com.alimberdi.gatekeeper.util;

import com.alimberdi.gatekeeper.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsernameGenerator {

	private final UserService userService;

	public String generate(String email) {
		String baseUsername = email.split("@")[0];
		String finalUsername = baseUsername;

		int suffix = 1;
		while (userService.existsByUsername(finalUsername)) {
			finalUsername = baseUsername + suffix;
			suffix++;
		}

		return finalUsername;
	}

}
