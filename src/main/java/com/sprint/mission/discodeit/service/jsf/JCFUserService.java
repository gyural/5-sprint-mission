package com.sprint.mission.discodeit.service.jsf;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.UserCreateDTO;
import com.sprint.mission.discodeit.domain.dto.UserUpdateDTO;
import com.sprint.mission.discodeit.domain.entity.User;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.UserService;

public class JCFUserService implements UserService {

	private final JCFUserRepository userRepository;

	public JCFUserService(JCFUserRepository userRepository) {
		this.userRepository = userRepository;
	}

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

		return userRepository.save(new User(username, email, password));

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

		if (newUsername == null || newUsername.isEmpty()) {
			throw new IllegalArgumentException("Username cannot be null or empty");
		}
		if (newEmail == null || newEmail.isEmpty()) {
			throw new IllegalArgumentException("Email cannot be null or empty");
		}
		if (newPassword == null || newPassword.isEmpty()) {
			throw new IllegalArgumentException("Password cannot be null or empty");
		}
		User targetUser = userRepository.find(userId)
		  .orElseThrow(() -> new NoSuchElementException("User with ID " + userId + " not found"));
		targetUser.setUsername(newUsername);
		targetUser.setEmail(newEmail);
		targetUser.setPassword(newPassword);

		userRepository.save(targetUser);
	}

	@Override
	public User read(UUID userId) {
		return userRepository.find(userId)
		  .orElseThrow(() -> new NoSuchElementException("User with ID " + userId + " not found"));
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
