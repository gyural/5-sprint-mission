package com.sprint.mission.discodeit.service;

import java.util.UUID;

public interface UserService {
	void create(String username);

	void delete(UUID userId);

	void update(UUID userId, String newUsername);

	void read(UUID userId);

}
