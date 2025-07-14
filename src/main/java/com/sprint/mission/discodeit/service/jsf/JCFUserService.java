package com.sprint.mission.discodeit.service.jsf;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

public class JCFUserService implements UserService {
	private static final Map<String, User> data = new HashMap<>();

	@Override
	public void create(String username) {

	}

	@Override
	public void delete(UUID userId) {

	}

	@Override
	public void update(UUID userId, String newUsername) {

	}

	@Override
	public void read(UUID userId) {

	}

}
