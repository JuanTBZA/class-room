package com.juantirado.virtual_classroom.common.api;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(
		int status,
		String error,
		String message,
		String path,
		Instant timestamp,
		Map<String, String> details
) {
	public static ErrorResponse of(
			int status,
			String error,
			String message,
			String path,
			Map<String, String> details
	) {
		return new ErrorResponse(status, error, message, path, Instant.now(), details);
	}
}
