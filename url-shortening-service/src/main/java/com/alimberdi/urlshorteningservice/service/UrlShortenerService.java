package com.alimberdi.urlshorteningservice.service;

import com.alimberdi.urlshorteningservice.dto.UrlRecordResponse;
import com.alimberdi.urlshorteningservice.exception.ResourceNotFoundException;
import com.alimberdi.urlshorteningservice.mapper.UrlRecordMapper;
import com.alimberdi.urlshorteningservice.repository.UrlRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {

	private final UrlRecordRepository repository;

	public UrlRecordResponse resolve(String shortCode) {
		return repository.findByShortCode(shortCode)
				.map(UrlRecordMapper::toResponse)
				.orElseThrow(() -> new ResourceNotFoundException("Url record with shortCode '" + shortCode + "' not found"));
	}

}
