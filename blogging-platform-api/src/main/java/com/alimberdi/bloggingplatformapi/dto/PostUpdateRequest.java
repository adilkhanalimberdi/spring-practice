package com.alimberdi.bloggingplatformapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record PostUpdateRequest(
		@NotBlank(message = "Title cannot be blank")
		@Size(min = 3, max = 50, message = "The size of title must be between 3 and 50 characters")
		String title,

		@NotBlank(message = "Content cannot be blank")
		@Size(max = 100_000, message = "Content should not exceed 100,000 characters")
		String content,

		@NotBlank(message = "Category cannot be blank")
		@Size(max = 50, message = "Category should not exceed 50 characters")
		String category,

		@NotEmpty(message = "At least one tag is required")
		Set<@NotBlank(message = "Individual tag cannot be blank") String> tags
) {}
