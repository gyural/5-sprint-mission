package com.sprint.mission.discodeit.service.file;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.service.UserService;

public class FileUserService implements UserService {

	private final FileUserRepository userRepository;

	public FileUserService(FileUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User create(String username, String email, String password) {

		if (username == null || username.isEmpty()) {
			throw new IllegalArgumentException("Username cannot be null or empty");
		}
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Email cannot be null or empty");
		}
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Password cannot be null or empty");
		}

		return userRepository.create(username, email, password);
	}

	@Override
	public void delete(UUID userId) {
		userRepository.delete(userId);
	}

	@Override
	public void update(UUID userId, String newUsername, String newEmail, String newPassword) {

		if (newUsername == null || newUsername.isEmpty()) {
			throw new IllegalArgumentException("New username cannot be null or empty");
		}
		if (newEmail == null || newEmail.isEmpty()) {
			throw new IllegalArgumentException("New email cannot be null or empty");
		}
		if (newPassword == null || newPassword.isEmpty()) {
			throw new IllegalArgumentException("New password cannot be null or empty");
		}

		userRepository.update(userId, newUsername, newEmail, newPassword);
	}

	@Override
	public User read(UUID userId) {
		return userRepository.find(userId);
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
