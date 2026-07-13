package com.alimberdi.gatekeeper.service;

import com.alimberdi.gatekeeper.dto.ProfileResponse;
import com.alimberdi.gatekeeper.dto.RegisterRequest;
import com.alimberdi.gatekeeper.entity.User;
import com.alimberdi.gatekeeper.enums.Provider;
import com.alimberdi.gatekeeper.enums.UserRole;
import com.alimberdi.gatekeeper.exception.ResourceNotFoundException;
import com.alimberdi.gatekeeper.mapper.UserMapper;
import com.alimberdi.gatekeeper.repository.UserRepository;
import com.alimberdi.gatekeeper.util.UsernameGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;

	public ProfileResponse getProfile(UUID id) {
		return repository.findById(id)
				.map(UserMapper::toProfileResponse)
				.orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
	}

	public ProfileResponse getProfile(String username) {
		return repository.findByUsername(username)
				.map(UserMapper::toProfileResponse)
				.orElseThrow(() -> new ResourceNotFoundException("User with username " + username + " not found"));
	}

	public User getByUsername(String username) {
		return repository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User with username " + username + " not found"));
	}

	public User getByEmail(String email) {
		return repository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
	}

	public boolean existsByUsername(String username) {
		return repository.existsByUsername(username);
	}

	public boolean existsByEmail(String email) {
		return repository.existsByEmail(email);
	}

	@Transactional(rollbackFor = Exception.class)
	public void create(RegisterRequest request) {
		User user = User.builder()
				.username(request.username())
				.email(request.email())
				.password(passwordEncoder.encode(request.password()))
				.role(UserRole.USER)
				.provider(Provider.LOCAL)
				.enabled(false)
				.build();

		repository.save(user);
	}

	@Transactional(rollbackFor = Exception.class)
	public void createGoogleUser(String email, String username) {
		String randomPassword = UUID.randomUUID().toString();

		User user = User.builder()
				.username(username)
				.email(email)
				.password(passwordEncoder.encode(randomPassword))
				.role(UserRole.USER)
				.provider(Provider.GOOGLE)
				.enabled(true)
				.build();

		repository.save(user);
	}

	@Transactional(rollbackFor = Exception.class)
	public void createGithubUser(String email, String username) {
		String randomPassword = UUID.randomUUID().toString();

		User user = User.builder()
				.username(username)
				.email(email)
				.password(passwordEncoder.encode(randomPassword))
				.role(UserRole.USER)
				.provider(Provider.GITHUB)
				.enabled(true)
				.build();

		repository.save(user);
	}

}
