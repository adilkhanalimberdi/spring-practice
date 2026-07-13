package com.alimberdi.gatekeeper.controller;

import com.alimberdi.gatekeeper.dto.AuthResponse;
import com.alimberdi.gatekeeper.dto.LoginRequest;
import com.alimberdi.gatekeeper.dto.RegisterRequest;
import com.alimberdi.gatekeeper.dto.VerifyRequest;
import com.alimberdi.gatekeeper.exception.ResourceAlreadyExistsException;
import com.alimberdi.gatekeeper.service.AuthService;
import com.alimberdi.gatekeeper.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(authService.login(request));
	}

	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterRequest request) {
		authService.register(request);
		return ResponseEntity
				.status(HttpStatus.ACCEPTED)
				.body(Map.of("email", request.email()));
	}

	@PostMapping("/verify")
	public ResponseEntity<AuthResponse> verify(@Valid @RequestBody VerifyRequest request) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(authService.verify(request));
	}

}
