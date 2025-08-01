package com.sprint.mission.discodeit.repository;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.ReadStatus;

public interface ReadStatusRepository {

	public ReadStatus create(UUID userId, UUID channelId);

	public ReadStatus find(UUID id);

	public List<ReadStatus> findAll();

	public void delete(UUID id);

	public void update(UUID id);

	boolean isEmpty(UUID id);

	void deleteAll();
}
