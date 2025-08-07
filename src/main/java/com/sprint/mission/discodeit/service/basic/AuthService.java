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
