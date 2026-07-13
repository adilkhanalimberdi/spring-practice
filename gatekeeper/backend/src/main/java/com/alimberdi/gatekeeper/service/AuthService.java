package com.alimberdi.gatekeeper.service;

import com.alimberdi.gatekeeper.dto.AuthResponse;
import com.alimberdi.gatekeeper.dto.LoginRequest;
import com.alimberdi.gatekeeper.dto.RegisterRequest;
import com.alimberdi.gatekeeper.dto.VerifyRequest;
import com.alimberdi.gatekeeper.entity.User;
import com.alimberdi.gatekeeper.enums.Provider;
import com.alimberdi.gatekeeper.exception.ResourceAlreadyExistsException;
import com.alimberdi.gatekeeper.exception.VerificationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final JwtService jwtService;
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final VerificationService verificationService;

	public AuthResponse login(LoginRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.username(), request.password())
		);

		User user = userService.getByUsername(request.username());

		if (!user.isEnabled()) {
			throw new VerificationFailedException("User is not verified");
		}

		String token = jwtService.generateToken(user.getUsername());
		return new AuthResponse(token);
	}

	@Transactional(rollbackFor = Exception.class)
	public void register(RegisterRequest request) {
		if (userService.existsByUsername(request.username())) {
			throw new ResourceAlreadyExistsException("User with this username already exists");
		}

		userService.create(request);
		verificationService.sendVerificationCode(request.email());
	}

	@Transactional(rollbackFor = Exception.class)
	public AuthResponse verify(VerifyRequest request) {
		String realCode = verificationService.getVerificationCode(request.email());

		if (!realCode.equals(request.code())) {
			throw new VerificationFailedException("Verification code is incorrect");
		}

		User user = userService.getByEmail(request.email());
		user.setEnabled(true);

		String token = jwtService.generateToken(user.getUsername());

		verificationService.removeVerificationCode(user.getEmail());
		return new AuthResponse(token);
	}

}
