package com.sprint.mission.discodeit.service.jsf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

public class JCFUserService implements UserService {
	public final Map<UUID, User> data;

	public JCFUserService() {
		data = new HashMap<>();
	}

	@Override
	public void create(String username) {
		if (username == null || username.isEmpty()) {
			throw new IllegalArgumentException("Username cannot be null or empty");
		}
		User user = new User(username);
		data.put(user.getId(), user);
	}

	@Override
	public void delete(UUID userId) {
		if (!data.containsKey(userId)) {
			throw new IllegalArgumentException("User with ID " + userId + " not found");
		}
		data.remove(userId);
	}

	@Override
	public void update(UUID userId, String newUsername) {
		if (!data.containsKey(userId)) {
			throw new IllegalArgumentException("User with ID " + userId + " not found");
		}
		if (newUsername == null || newUsername.isEmpty()) {
			throw new IllegalArgumentException("New username cannot be null or empty");
		}

		User user = data.get(userId);
		user.setUsername(newUsername);
		user.setUpdatedAt(System.currentTimeMillis());
	}

	@Override
	public User read(UUID userId) {
		if (!data.containsKey(userId)) {
			throw new IllegalArgumentException("User with ID " + userId + " not found");
		}
		return data.get(userId);
	}

	@Override
	public List<User> readAll() {
		return data.values().stream().toList();
	}

	@Override
	public boolean isEmpty(UUID userId) {
		return data.get(userId) == null;
	}

	@Override
	public void deleteAll() {
		data.clear();
	}
}
