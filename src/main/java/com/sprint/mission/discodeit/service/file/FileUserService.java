package com.sprint.mission.discodeit.service.file;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

public class FileUserService implements UserService {

	@Override
	public User create(String username, String email, String password) {
		return null;
	}

	@Override
	public void delete(UUID userId) {

	}

	@Override
	public void update(UUID userId, String newUsername, String newEmail, String newPassword) {

	}

	@Override
	public User read(UUID userId) {
		return null;
	}

	@Override
	public List<User> readAll() {
		return List.of();
	}

	@Override
	public boolean isEmpty(UUID userId) {
		return false;
	}

	@Override
	public void deleteAll() {

	}
}
