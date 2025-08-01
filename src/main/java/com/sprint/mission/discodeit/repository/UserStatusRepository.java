package com.sprint.mission.discodeit.repository;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.UserStatus;

public interface UserStatusRepository {

	public UserStatus create(UUID userId);

	public UserStatus find(UUID id);

	public List<UserStatus> findAll();

	public void delete(UUID id);

	public void update(UUID id);

	boolean isEmpty(UUID id);

	void deleteAll();
}
