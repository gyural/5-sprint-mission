package com.sprint.mission.discodeit.service.basic;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sprint.mission.discodeit.domain.dto.UserCreateDTO;
import com.sprint.mission.discodeit.domain.dto.UserUpdateDTO;
import com.sprint.mission.discodeit.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.domain.entity.User;
import com.sprint.mission.discodeit.domain.entity.UserStatus;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {

	private final UserRepository userRepository;
	private final BinaryContentRepository binaryContentRepository;
	private final UserStatusRepository userStatusRepository;

	@Override
	public User create(UserCreateDTO dto) {
		Optional.ofNullable(dto).orElseThrow(() -> new IllegalArgumentException("UserCreateDTO cannot be null"));
		String username = dto.getUsername();
		String email = dto.getEmail();
		String password = dto.getPassword();

		if (username == null || username.isEmpty()) {
			throw new IllegalArgumentException("Username cannot be null or empty");
		}
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Email cannot be null or empty");
		}
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Password cannot be null or empty");
		}

		// 1) Profile insert
		BinaryContent profileImage = binaryContentRepository.create(dto.getBinaryContent());
		// 2) User insert
		User newUser = userRepository.create(new User(username, email, password, profileImage.getId()));
		// 3) UserStatus insert
		userStatusRepository.create(new UserStatus(newUser.getId()));

		return newUser;
	}

	@Override
	public void delete(UUID userId) {
		userRepository.delete(userId);
	}

	@Override
	public void update(UserUpdateDTO dto) {
		Optional.ofNullable(dto).orElseThrow(() -> new IllegalArgumentException("UserUpdateDTO cannot be null"));
		UUID userId = dto.getUserId();
		String newUsername = dto.getNewUsername();
		String newEmail = dto.getNewEmail();
		String newPassword = dto.getNewPassword();

		if (userId == null) {
			throw new IllegalArgumentException("User ID cannot be null");
		}
		if (newUsername == null || newUsername.isEmpty()) {
			throw new IllegalArgumentException("New username cannot be null or empty");
		}
		if (newEmail == null || newEmail.isEmpty()) {
			throw new IllegalArgumentException("New email cannot be null or empty");
		}
		if (newPassword == null || newPassword.isEmpty()) {
			throw new IllegalArgumentException("New password cannot be null or empty");
		}

		User targetUser = userRepository.find(userId)
		  .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " does not exist"));

		userRepository.update(userId, targetUser);
	}

	@Override
	public User read(UUID userId) {
		return userRepository.find(userId)
		  .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " does not exist"));
	}

	@Override
	public List<User> readAll() {
		return userRepository.findAll();
	}

	@Override
	public boolean isEmpty(UUID userId) {
		return userRepository.isEmpty(userId);
	}

	@Override
	public void deleteAll() {
		userRepository.deleteAll();

	}
}
