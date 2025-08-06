package com.sprint.mission.discodeit.service.jsf;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.UserCreateDTO;
import com.sprint.mission.discodeit.domain.dto.UserReadDTO;
import com.sprint.mission.discodeit.domain.dto.UserUpdateDTO;
import com.sprint.mission.discodeit.domain.entity.User;
import com.sprint.mission.discodeit.domain.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JCFUserService implements UserService {

	private final JCFUserRepository userRepository;
	private final UserStatusRepository userStatusRepository;

	/**
	 * Creates a new user with the provided details.
	 *
	 * Validates the input data and ensures the username is unique before saving the user.
	 *
	 * @param dto Data transfer object containing the username, email, and password for the new user.
	 * @return The newly created User entity.
	 * @throws IllegalArgumentException if the input DTO is null, any required field is null or empty, or the username already exists.
	 */
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

		if (userRepository.findByUsername(username) != null) {
			throw new IllegalArgumentException("Username already exists");
		}

		return userRepository.save(new User(username, email, password, null));

	}

	/**
	 * Deletes the user identified by the given user ID from the repository.
	 *
	 * @param userId the unique identifier of the user to delete
	 */
	@Override
	public void delete(UUID userId) {
		userRepository.delete(userId);
	}

	/**
	 * Updates an existing user's username, email, and password with the values provided in the given DTO.
	 *
	 * @param dto Data transfer object containing the user ID and new values for username, email, and password.
	 * @throws IllegalArgumentException if the DTO is null or any of the new values are empty.
	 * @throws NoSuchElementException if no user with the specified ID exists.
	 */
	@Override
	public void update(UserUpdateDTO dto) {
		Optional.ofNullable(dto).orElseThrow(() -> new IllegalArgumentException("UserUpdateDTO cannot be null"));
		UUID userId = dto.getUserId();
		String newUsername = dto.getNewUsername();
		String newEmail = dto.getNewEmail();
		String newPassword = dto.getNewPassword();

		if (newUsername.isEmpty()) {
			throw new IllegalArgumentException("Username cannot be empty");
		}
		if (newEmail.isEmpty()) {
			throw new IllegalArgumentException("Email cannot be empty");
		}
		if (newPassword.isEmpty()) {
			throw new IllegalArgumentException("Password cannot be empty");
		}
		User targetUser = userRepository.find(userId)
		  .orElseThrow(() -> new NoSuchElementException("User with ID " + userId + " not found"));
		targetUser.setUsername(newUsername);
		targetUser.setEmail(newEmail);
		targetUser.setPassword(newPassword);

		userRepository.save(targetUser);
	}

	/**
	 * Retrieves user details and online status for the specified user ID.
	 *
	 * @param userId the unique identifier of the user to retrieve
	 * @return a UserReadDTO containing user information and online status
	 * @throws IllegalArgumentException if no user with the given ID exists
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
	 * @return a list containing all User entities
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
