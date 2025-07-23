package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.User;

public interface UserService {
	User create(String username, String email, String password);

	void delete(UUID userId);

	void update(UUID userId, String newUsername, String newEmail, String newPassword);

	User read(UUID userId);

	List<User> readAll();

	boolean isEmpty(UUID userId);

	void deleteAll();

}
