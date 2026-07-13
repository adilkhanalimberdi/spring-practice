package com.alimberdi.gatekeeper.oauth2;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuth2ProviderStrategy {

	String getProviderName();
	String extractEmail(OAuth2User oAuth2User);
	String extractUsername(OAuth2User oAuth2User, String email);
	void createUser(String email, String username);

}
