package com.alimberdi.urlshorteningservice.dto;

import java.time.LocalDateTime;

public record UrlRecordStatistics(
		Long id,
		String url,
		String shortCode,
		LocalDateTime createdAt,
		LocalDateTime updatedAt,
		Long accessCount
) {}
