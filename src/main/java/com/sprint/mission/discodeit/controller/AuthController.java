package com.sprint.mission.discodeit.controller;

import static com.sprint.mission.discodeit.service.basic.BasicAuthService.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.mission.discodeit.domain.dto.LoginParams;
import com.sprint.mission.discodeit.domain.entity.User;
import com.sprint.mission.discodeit.domain.request.UserLoginRequest;
import com.sprint.mission.discodeit.domain.response.UserLoginResponse;
import com.sprint.mission.discodeit.service.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "인증 API")

public class AuthController {
	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest request) {

		User user = authService.login(
		  LoginParams.builder()
			.username(request.getUsername())
			.password(request.getPassword())
			.build()
		);

		return ResponseEntity.ok(toUserLoginResponse(user));
	}

}
