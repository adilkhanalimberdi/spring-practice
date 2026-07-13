package com.alimberdi.gatekeeper.oauth2;

import com.alimberdi.gatekeeper.service.JwtService;
import com.alimberdi.gatekeeper.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtService jwtService;
	private final UserService userService;
	private final Map<String, OAuth2ProviderStrategy> strategyMap;

	public OAuth2SuccessHandler(
			JwtService jwtService,
			UserService userService,
			List<OAuth2ProviderStrategy> strategies
	) {
		this.jwtService = jwtService;
		this.userService = userService;
		this.strategyMap = strategies.stream()
				.collect(Collectors.toMap(OAuth2ProviderStrategy::getProviderName, Function.identity()));
	}

	@Override
	@NullMarked
	public void onAuthenticationSuccess(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication
	) throws IOException {
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		Objects.requireNonNull(oAuth2User);

		String providerName = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();

		OAuth2ProviderStrategy strategy = strategyMap.get(providerName);
		if (strategy == null) {
			throw new IllegalArgumentException("Unsupported OAuth2 provider: " + providerName);
		}

		String email = strategy.extractEmail(oAuth2User);
		String username;

		if (!userService.existsByEmail(email)) {
			username = strategy.extractUsername(oAuth2User, email);
		} else {
			username = userService.getByEmail(email).getUsername();
		}

		String token = jwtService.generateToken(username);
		String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:5173/oauth2/redirect")
				.queryParam("token", token)
				.build().toUriString();
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
}
