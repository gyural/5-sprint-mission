package com.sprint.mission.discodeit.service.basic;

import org.springframework.stereotype.Service;

import com.sprint.mission.discodeit.domain.entity.User;
import com.sprint.mission.discodeit.domain.request.UserLoginRequest;
import com.sprint.mission.discodeit.domain.response.UserLoginResponse;
import com.sprint.mission.discodeit.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;

	/**
	 * Authenticates a user based on the provided login request.
	 *
	 * Attempts to find a user by username and verifies the password. Throws a {@code RuntimeException}
	 * if the user is not found or if the password is invalid. Returns a {@code UserLoginResponse}
	 * indicating successful authentication and including the authenticated user.
	 *
	 * @param request the login request containing username and password
	 * @return a response indicating successful authentication and the authenticated user
	 * @throws RuntimeException if the user is not found or the password is invalid
	 */
	public UserLoginResponse login(UserLoginRequest request) {
		User user = userRepository.findByUsername(request.getUsername())
		  .orElseThrow(() -> new RuntimeException("User not found"));

		if (!user.getPassword().equals(request.getPassword())) {
			throw new RuntimeException("Invalid password");
		}

		return UserLoginResponse.builder()
		  .success(true)
		  .user(user)
		  .build();
	}

}
