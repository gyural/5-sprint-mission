package com.sprint.mission.discodeit.service.basic;

import java.util.List;
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

	/**
	 * Creates a new user with the provided information.
	 *
	 * Validates the input DTO and its required fields. If a profile image is included, it is saved and associated with the new user. Throws an exception if the username or email already exists.
	 *
	 * @param dto the data transfer object containing user creation details
	 * @return the newly created User entity
	 * @throws IllegalArgumentException if the DTO or any required field is null or empty
	 * @throws RuntimeException if the username or email already exists
	 */
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

		// 3. 데이터 저장
		return userRepository.save(newUser);
	}

	/**
	 * Deletes a user and all associated data by user ID.
	 *
	 * Removes the user's status records and profile image if present before deleting the user entity itself.
	 *
	 * @param userId the unique identifier of the user to delete
	 * @throws IllegalArgumentException if the user with the specified ID does not exist
	 */
	@Override
	public void delete(UUID userId) {
		User targetUser = userRepository.find(userId)
		  .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " does not exist"));

		// 1. User Status 삭제
		userStatusRepository.deleteByUserId(userId);
		// 2. Profile Image 삭제
		if (binaryContentRepository.find(targetUser.getProfileId()).isPresent()) {
			binaryContentRepository.delete(targetUser.getProfileId());
		}
		// 3. User 삭제
		userRepository.delete(userId);
	}

	/**
	 * Updates an existing user's information with new username, email, password, and optionally a new profile image.
	 *
	 * @param dto Data transfer object containing the user ID and updated user details.
	 * @throws IllegalArgumentException if the DTO or any required field is null or empty, or if the user does not exist.
	 */
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

	/**
	 * Retrieves user details and online status for the specified user ID.
	 *
	 * @param userId the unique identifier of the user to retrieve
	 * @return a UserReadDTO containing user information and online status
	 * @throws IllegalArgumentException if the user with the given ID does not exist
	 */
	@Override
	public UserReadDTO read(UUID userId) {
		User user = userRepository.find(userId)
		  .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));

		Optional<UserStatus> status = userStatusRepository.findByUserId(userId);

		boolean isOnline = status.stream()
		  .anyMatch(UserStatus::isOnline);

		return UserReadDTO.builder()
		  .id(user.getId())
		  .createdAt(user.getCreatedAt())
		  .updatedAt(user.getUpdatedAt())
		  .username(user.getUsername())
		  .email(user.getEmail())
		  .profileId(user.getProfileId())
		  .isOnline(isOnline)
		  .build();
	}

	/**
	 * Retrieves a list of all users.
	 *
	 * @return a list containing all user entities
	 */
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
