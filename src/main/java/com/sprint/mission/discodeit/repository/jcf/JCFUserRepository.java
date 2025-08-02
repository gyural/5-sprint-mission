package com.sprint.mission.discodeit.repository.jcf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

public class JCFUserRepository implements UserRepository {

	private final Map<UUID, User> data;

	public JCFUserRepository() {
		data = new HashMap<>();
	}

	@Override
	public User create(User user) {
		if (user == null || user.getUsername() == null || user.getEmail() == null || user.getPassword() == null) {
			throw new IllegalArgumentException("User, username, email, and password must not be null");
		}
		String username = user.getUsername();
		String email = user.getEmail();
		String password = user.getPassword();

		User newUser = new User(username, email, password, null);
		data.put(newUser.getId(), newUser);
		return newUser;
	}

	@Override
	public void delete(UUID userId) {
		if (!data.containsKey(userId)) {
			throw new IllegalArgumentException("User with ID " + userId + " not found");
		}
		data.remove(userId);
	}

	@Override
	public void update(UUID userId, User user) {
		data.get(userId).setUsername(user.getUsername());
		data.get(userId).setEmail(user.getEmail());
		data.get(userId).setPassword(user.getPassword());
	}

	@Override
	public Optional<User> find(UUID userId) {
		return Optional.ofNullable(data.get(userId));
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

	@Override
	public Long count() {
		return (long)data.size();
	}
}
