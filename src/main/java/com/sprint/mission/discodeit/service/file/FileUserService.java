package com.sprint.mission.discodeit.service.file;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.UserCreateDTO;
import com.sprint.mission.discodeit.domain.dto.UserReadDTO;
import com.sprint.mission.discodeit.domain.dto.UserUpdateDTO;
import com.sprint.mission.discodeit.domain.entity.User;
import com.sprint.mission.discodeit.domain.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileUserService implements UserService {

	private final FileUserRepository userRepository;
	private final UserStatusRepository userStatusRepository;

	/**
	 * Creates a new user with the provided username, email, and password.
	 *
	 * Validates that the username, email, and password in the given DTO are non-null and non-empty.
	 * Throws an IllegalArgumentException if any validation fails.
	 *
	 * @param dto the data transfer object containing user creation details
	 * @return the created User entity
	 * @throws IllegalArgumentException if username, email, or password is null or empty
	 */
	@Override
	public User create(UserCreateDTO dto) {
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

		return userRepository.save(new User(username, email, password, null));
	}

	/**
	 * Deletes the user identified by the specified user ID from the repository.
	 *
	 * @param userId the unique identifier of the user to delete
	 */
	@Override
	public void delete(UUID userId) {
		userRepository.delete(userId);
	}

	/**
	 * Updates an existing user's username, email, and password using the provided update DTO.
	 *
	 * @param dto the data transfer object containing the user ID and new user details
	 * @throws IllegalArgumentException if the DTO is null, any new field is null or empty, or the user does not exist
	 */
	@Override
	public void update(UserUpdateDTO dto) {
		Optional.ofNullable(dto).orElseThrow(() -> new IllegalArgumentException("UserUpdateDTO cannot be null"));
		UUID userId = dto.getUserId();
		String newUsername = dto.getNewUsername();
		String newEmail = dto.getNewEmail();
		String newPassword = dto.getNewPassword();

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
		  .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));

		targetUser.setUsername(newUsername);
		targetUser.setEmail(newEmail);
		targetUser.setPassword(newPassword);

		userRepository.save(targetUser);
	}

	/**
	 * Retrieves user details and online status for the specified user ID.
	 *
	 * @param userId the unique identifier of the user to retrieve
	 * @return a {@link UserReadDTO} containing user information and online status
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
	 * Retrieves a list of all users from the repository.
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
