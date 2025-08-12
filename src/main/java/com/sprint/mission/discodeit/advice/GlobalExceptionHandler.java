package com.sprint.mission.discodeit.advice;

import static org.springframework.http.HttpStatus.*;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sprint.mission.discodeit.domain.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		// Log the exception (you can use a logging framework like SLF4J)
		logger.error("Unexpected Error 발생: {}", e.getMessage());

		return ResponseEntity.internalServerError().body(ErrorResponse.builder()
		  .status(INTERNAL_SERVER_ERROR.value())
		  .errMessage("Unexpected Error 발생: " + e.getMessage())
		  .build());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		// 에러 메시지 추출 및 응답 생성
		String errorMessage = ex.getBindingResult()
		  .getFieldErrors()
		  .stream()
		  .map(error -> error.getField() + ": " + error.getDefaultMessage())
		  .findFirst()
		  .orElse("잘못된 요청입니다.");
		return ResponseEntity.badRequest().body(ErrorResponse.builder()
		  .status(BAD_REQUEST.value())
		  .errMessage(errorMessage)
		  .build());
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
		// 에러 메시지 추출 및 응답 생성
		String errorMessage = ex.getMessage();
		return ResponseEntity.badRequest().body(ErrorResponse.builder()
		  .status(BAD_REQUEST.value())
		  .errMessage(errorMessage)
		  .build());
	}
}
