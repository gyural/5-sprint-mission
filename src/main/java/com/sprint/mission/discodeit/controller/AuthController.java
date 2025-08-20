package com.sprint.mission.discodeit.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	@RequestMapping(value = "/login", method = POST)
	public ResponseEntity<UserLoginResponse> login(@ModelAttribute @Valid UserLoginRequest request) {

		User user = authService.login(
		  LoginParams.builder()
			.username(request.getUsername())
			.password(request.getPassword())
			.build()
		);

		return ResponseEntity.ok(UserLoginResponse.builder()
		  .id(user.getId())
		  .username(user.getUsername())
		  .email(user.getEmail())
		  .createdAt(user.getCreatedAt())
		  .updatedAt(user.getUpdatedAt())
		  .id(user.getId())
		  .profileId(user.getProfileId())
		  .build());
	}

}
