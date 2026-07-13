package com.alimberdi.gatekeeper.dto;

public record VerifyRequest(
		String email,
		String code
) {}
