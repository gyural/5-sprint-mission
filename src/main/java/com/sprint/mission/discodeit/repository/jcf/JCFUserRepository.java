package com.sprint.mission.discodeit.repository.jcf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

public class JCFUserRepository implements UserRepository {

	private final Map<UUID, User> data;

	public JCFUserRepository() {
		data = new HashMap<>();
	}

	@Override
	public User create(String username, String email, String password) {

		User user = new User(username, email, password);
		data.put(user.getId(), user);
		return user;
	}

	@Override
	public void delete(UUID userId) {
		if (!data.containsKey(userId)) {
			throw new IllegalArgumentException("User with ID " + userId + " not found");
		}
		data.remove(userId);
	}

	@Override
	public void update(UUID userId, String newUsername, String newEmail, String newPassword) {
		data.get(userId).setUsername(newUsername);
		data.get(userId).setEmail(newEmail);
		data.get(userId).setPassword(newPassword);
	}

	@Override
	public User find(UUID userId) {
		return Optional.ofNullable(data.get(userId))
		  .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));
	}

	@Override
	public List<User> findAll() {
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

	public User findByUsername(String username) {
		return data.values().stream()
		  .filter(user -> user.getUsername().equals(username))
		  .findFirst()
		  .orElse(null);
	}
}
