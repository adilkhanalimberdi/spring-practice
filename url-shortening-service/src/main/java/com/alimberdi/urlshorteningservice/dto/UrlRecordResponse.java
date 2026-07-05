package com.alimberdi.urlshorteningservice.dto;

import java.time.LocalDateTime;

public record UrlRecordResponse(
		Long id,
		String url,
		String shortCode,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {}
