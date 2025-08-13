package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.CreateUserDTO;
import com.sprint.mission.discodeit.domain.dto.UpdateUserDTO;
import com.sprint.mission.discodeit.domain.entity.User;
import com.sprint.mission.discodeit.domain.response.UserDeleteResponse;
import com.sprint.mission.discodeit.domain.response.UserReadResponse;
import com.sprint.mission.discodeit.domain.response.UserUpdateResponse;

public interface UserService {
	User create(CreateUserDTO dto);

	UserDeleteResponse delete(UUID userId);

	UserUpdateResponse update(UpdateUserDTO dto);

	UserReadResponse read(UUID userId);

	List<UserReadResponse> readAll();

	boolean isEmpty(UUID userId);

	void deleteAll();

}
