package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.UserCreateDTO;
import com.sprint.mission.discodeit.domain.dto.UserReadDTO;
import com.sprint.mission.discodeit.domain.dto.UserUpdateDTO;
import com.sprint.mission.discodeit.domain.entity.User;

public interface UserService {
	User create(UserCreateDTO dto);

	void delete(UUID userId);

	void update(UserUpdateDTO dto);

	UserReadDTO read(UUID userId);

	List<User> readAll();

	boolean isEmpty(UUID userId);

	void deleteAll();

}
