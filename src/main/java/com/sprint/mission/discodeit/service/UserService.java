package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.dto.UserCreateDTO;
import com.sprint.mission.discodeit.dto.UserUpdateDTO;
import com.sprint.mission.discodeit.entity.User;

public interface UserService {
	User create(UserCreateDTO dto);

	void delete(UUID userId);

	void update(UserUpdateDTO dto);

	User read(UUID userId);

	List<User> readAll();

	boolean isEmpty(UUID userId);

	void deleteAll();

}
