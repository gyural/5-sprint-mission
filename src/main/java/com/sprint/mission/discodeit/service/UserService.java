package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.CreateUserDTO;
import com.sprint.mission.discodeit.domain.dto.UpdateUserDTO;
import com.sprint.mission.discodeit.domain.entity.User;
import com.sprint.mission.discodeit.domain.response.UserReadResponse;

public interface UserService {
	User create(CreateUserDTO dto);

	void delete(UUID userId);

	void update(UpdateUserDTO dto);

	UserReadResponse read(UUID userId);

	List<User> readAll();

	boolean isEmpty(UUID userId);

	void deleteAll();

}
