package com.sprint.mission.discodeit.service;

public interface UserService {
	void create(String username);

	void delete(String userId);

	void update(String userId, String newUsername);

	void read(String userId);
}
