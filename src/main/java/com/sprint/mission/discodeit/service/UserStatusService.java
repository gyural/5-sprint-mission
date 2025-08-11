package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.CreateUserStatusDTO;
import com.sprint.mission.discodeit.domain.dto.UpdateUserStatusDTO;
import com.sprint.mission.discodeit.domain.entity.UserStatus;

public interface UserStatusService {

	public UserStatus create(CreateUserStatusDTO dto);

	public UserStatus find(UUID id);

	public List<UserStatus> findAll();

	public void update(UpdateUserStatusDTO dto);

	public void updateByUserId(UUID userID);

	public void delete(UUID id);
}
