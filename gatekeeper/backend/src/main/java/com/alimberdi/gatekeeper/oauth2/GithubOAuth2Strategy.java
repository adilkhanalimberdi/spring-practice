package com.alimberdi.gatekeeper.oauth2;

import com.alimberdi.gatekeeper.service.UserService;
import com.alimberdi.gatekeeper.util.UsernameGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GithubOAuth2Strategy implements OAuth2ProviderStrategy {

	private final UserService userService;
	private final UsernameGenerator usernameGenerator;

	@Override
	public String getProviderName() {
		return "github";
	}

	@Override
	public String extractEmail(OAuth2User oAuth2User) {
		String email = oAuth2User.getAttribute("email");
		if (email == null) {
			String githubLogin = oAuth2User.getAttribute("login");
			email = githubLogin + "@github.com";
		}
		return email;
	}

	@Override
	public String extractUsername(OAuth2User oAuth2User, String email) {
		String githubLogin = oAuth2User.getAttribute("login");
		return (githubLogin != null) ? githubLogin : usernameGenerator.generate(email);
	}

	@Override
	public void createUser(String email, String username) {
		userService.createGithubUser(email, username);
	}

}
