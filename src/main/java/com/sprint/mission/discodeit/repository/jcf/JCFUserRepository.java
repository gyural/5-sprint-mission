package com.sprint.mission.discodeit.repository.jcf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.sprint.mission.discodeit.domain.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

@Repository
@ConditionalOnProperty(
  prefix = "discodeit.repository",
  name = "type",
  havingValue = "jcf",
  matchIfMissing = true // 값이 없으면 JCF로 등록
)
public class JCFUserRepository implements UserRepository {

	private final Map<UUID, User> data;

	public JCFUserRepository() {
		data = new HashMap<>();
	}

	@Override
	public User save(User user) {
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

	public Optional<User> findByUsername(String username) {
		return data.values().stream()
		  .filter(user -> user.getUsername().equals(username))
		  .findFirst();
	}

	@Override
	public Long count() {
		return (long)data.size();
	}

	@Override
	public Optional<User> findByEmail(String username) {
		return Optional.empty();
	}
}
