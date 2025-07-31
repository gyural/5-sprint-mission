package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.User;

public interface UserService {
	void create(String username);

	void delete(UUID userId);

	void update(UUID userId, String newUsername);

	User read(UUID userId);

	List<User> readAll();

	boolean isEmpty(UUID userId);

	void deleteAll();

}
