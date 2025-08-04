package com.sprint.mission.discodeit.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.entity.UserStatus;

public interface UserStatusRepository {

	public UserStatus save(UserStatus userStatus);

	public Optional<UserStatus> find(UUID id);

	public List<UserStatus> findByUserId(UUID userId);

	public List<UserStatus> findByChannelId(UUID channelId);

	public List<UserStatus> findAll();

	public void delete(UUID id);

	boolean isEmpty(UUID id);

	void deleteAll();

	void deleteByUserId(UUID userId);

	void deleteByChannelId(UUID channelId);
}
