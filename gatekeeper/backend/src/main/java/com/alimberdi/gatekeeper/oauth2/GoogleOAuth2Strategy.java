package com.alimberdi.gatekeeper.oauth2;

import com.alimberdi.gatekeeper.service.UserService;
import com.alimberdi.gatekeeper.util.UsernameGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class GoogleOAuth2Strategy implements OAuth2ProviderStrategy {

	private final UsernameGenerator usernameGenerator;
	private final UserService userService;

	@Override
	public String getProviderName() {
		return "google";
	}

	@Override
	public String extractEmail(OAuth2User oAuth2User) {
		return Objects.requireNonNull(oAuth2User.getAttribute("email"), "Google email cannot be null");
	}

	@Override
	public String extractUsername(OAuth2User oAuth2User, String email) {
		return usernameGenerator.generate(email);
	}

	@Override
	public void createUser(String email, String username) {
		userService.createGoogleUser(email, username);
	}

}
