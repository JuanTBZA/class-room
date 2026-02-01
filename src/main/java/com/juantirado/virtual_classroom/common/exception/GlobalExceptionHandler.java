package com.juantirado.virtual_classroom.common.exception;

import com.juantirado.virtual_classroom.common.api.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ErrorResponse> handleApiException(
			ApiException exception,
			HttpServletRequest request
	) {
		var status = exception.getStatus();
		var response = ErrorResponse.of(
				status.value(),
				status.getReasonPhrase(),
				exception.getMessage(),
				request.getRequestURI(),
				Map.of()
		);
		return ResponseEntity.status(status).body(response);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(
			MethodArgumentNotValidException exception,
			HttpServletRequest request
	) {
		Map<String, String> details = exception.getBindingResult()
				.getFieldErrors()
				.stream()
				.collect(Collectors.toMap(
						FieldError::getField,
						FieldError::getDefaultMessage,
						(existing, replacement) -> existing
				));

		var response = ErrorResponse.of(
				HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(),
				"Validation failed",
				request.getRequestURI(),
				details
		);

		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
			IllegalArgumentException exception,
			HttpServletRequest request
	) {
		var status = HttpStatus.BAD_REQUEST;
		var response = ErrorResponse.of(
				status.value(),
				status.getReasonPhrase(),
				exception.getMessage(),
				request.getRequestURI(),
				Map.of()
		);
		return ResponseEntity.status(status).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleUnexpectedException(
			Exception exception,
			HttpServletRequest request
	) {
		var status = HttpStatus.INTERNAL_SERVER_ERROR;
		var response = ErrorResponse.of(
				status.value(),
				status.getReasonPhrase(),
				"Unexpected error",
				request.getRequestURI(),
				Map.of()
		);
		return ResponseEntity.status(status).body(response);
	}
}
