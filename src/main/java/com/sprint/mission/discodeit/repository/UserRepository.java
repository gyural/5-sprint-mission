package com.sprint.mission.discodeit.repository;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.User;

public interface UserRepository {
	User create(String username, String email, String password);

	void delete(UUID userId);

	void update(UUID userId, String newUsername, String newEmail, String newPassword);

	User find(UUID userId);

	List<User> findAll();

	boolean isEmpty(UUID userId);

	void deleteAll();
}
