package com.sprint.mission.discodeit.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.mission.discodeit.domain.request.UserLoginRequest;
import com.sprint.mission.discodeit.domain.response.UserLoginResponse;
import com.sprint.mission.discodeit.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;

	@RequestMapping(value = "/login", method = POST)
	public ResponseEntity<UserLoginResponse> login(@ModelAttribute @Valid UserLoginRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}

}
