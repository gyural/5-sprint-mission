package com.sprint.mission.discodeit.advice;

import static org.springframework.http.HttpStatus.*;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.sprint.mission.discodeit.domain.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		// Log the eception (you can use a logging framework like SLF4J)
		logger.error("Unexpected Error 발생", e);
		return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ErrorResponse.builder()
		  .status(INTERNAL_SERVER_ERROR.value())
		  .errMessage("Unexpected Error 발생: " + e.getMessage())
		  .build());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(IllegalArgumentException e) {
		logger.error("Error 발생", e);

		// 에러 메시지 추출 및 응답 생성
		return ResponseEntity.status(BAD_REQUEST).body(ErrorResponse.builder()
		  .status(BAD_REQUEST.value())
		  .errMessage(e.getMessage())
		  .build());
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e) {
		logger.error("Unexpected Error 발생", e);

		// 에러 메시지 추출 및 응답 생성
		String errorMessage = e.getMessage();
		return ResponseEntity.status(NOT_FOUND).body(ErrorResponse.builder()
		  .status(NOT_FOUND.value())
		  .errMessage(errorMessage)
		  .build());
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoResourceFoundException e) {
		logger.error("Unexpected Error 발생", e);

		// 에러 메시지 추출 및 응답 생성
		String errorMessage = e.getMessage();
		return ResponseEntity.status(NOT_FOUND).body(ErrorResponse.builder()
		  .status(NOT_FOUND.value())
		  .errMessage(errorMessage)
		  .build());
	}
}
