package com.sprint.mission.discodeit.service.basic;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sprint.mission.discodeit.domain.dto.UserCreateDTO;
import com.sprint.mission.discodeit.domain.dto.UserReadDTO;
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
		BinaryContent profileImage = dto.getBinaryContent();

		if (username == null || username.isEmpty()) {
			throw new IllegalArgumentException("Username cannot be null or empty");
		}
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Email cannot be null or empty");
		}
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Password cannot be null or empty");
		}

		// 1. Check username 과 email 중복 여부
		if (userRepository.findByUsername(username).isPresent() || userRepository.findByEmail(email).isPresent()) {
			throw new RuntimeException("Username or email already exists");
		}

		// 2. Profile image 여부 반영
		User newUser = new User(
		  username,
		  email,
		  password,
		  profileImage != null ? binaryContentRepository.save(profileImage).getId() : null
		);

		// 3. userStatus 초기화
		userStatusRepository.save(new UserStatus(newUser.getId()));

		// 4. 데이터 저장
		return userRepository.save(newUser);
	}

	@Override
	public void delete(UUID userId) {
		User targetUser = userRepository.find(userId)
		  .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " does not exist"));

		// 1. User Status 삭제
		UserStatus userStatus = userStatusRepository.findByUserId(userId)
		  .orElseThrow(() -> new NoSuchElementException("UserStatus for user ID " + userId + " not found"));
		userStatusRepository.delete(userStatus.getId());
		// 2. Profile Image 삭제
		if (binaryContentRepository.find(targetUser.getProfileId()).isPresent()) {
			binaryContentRepository.delete(targetUser.getProfileId());
		}
		// 3. User 삭제
		userRepository.delete(userId);
	}

	@Override
	public void update(UserUpdateDTO dto) {
		Optional.ofNullable(dto).orElseThrow(() -> new IllegalArgumentException("UserUpdateDTO cannot be null"));
		UUID userId = dto.getUserId();
		String newUsername = dto.getNewUsername();
		String newEmail = dto.getNewEmail();
		String newPassword = dto.getNewPassword();
		BinaryContent newProfileImage = dto.getNewProfileImage();

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
		targetUser.setUsername(newUsername);
		targetUser.setEmail(newEmail);
		targetUser.setPassword(newPassword);

		if (newProfileImage != null) {
			BinaryContent savedProfileImage = binaryContentRepository.save(newProfileImage);
			targetUser.setProfileId(savedProfileImage.getId());
		}

		userRepository.save(targetUser);
	}

	@Override
	public UserReadDTO read(UUID userId) {
		User user = userRepository.find(userId)
		  .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));

		UserStatus status = userStatusRepository.findByUserId(userId).orElseThrow(
		  () -> new NoSuchElementException("UserStatus for user ID " + userId + " not found"));

		return UserReadDTO.builder()
		  .id(user.getId())
		  .createdAt(user.getCreatedAt())
		  .updatedAt(user.getUpdatedAt())
		  .username(user.getUsername())
		  .email(user.getEmail())
		  .profileId(user.getProfileId())
		  .isOnline(status.isOnline())
		  .build();
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
