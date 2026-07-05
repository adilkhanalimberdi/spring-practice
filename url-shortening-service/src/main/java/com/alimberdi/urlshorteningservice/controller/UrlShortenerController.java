package com.alimberdi.urlshorteningservice.controller;

import com.alimberdi.urlshorteningservice.service.UrlShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shorten")
public class UrlShortenerController {

	private	final UrlShortenerService urlShortenerService;

	@GetMapping("/{shortCode}")
	public ResponseEntity<?> getOriginalUrl(@PathVariable String shortCode) {
		return ResponseEntity.ok(urlShortenerService.resolve(shortCode));
	}

}
