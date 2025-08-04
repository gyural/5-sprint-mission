package com.sprint.mission.discodeit.repository;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.entity.ReadStatus;

public interface ReadStatusRepository {

	public ReadStatus save(UUID userId, UUID channelId);

	public ReadStatus find(UUID id);

	public List<ReadStatus> findAll();

	public void delete(UUID id);

	boolean isEmpty(UUID id);

	void deleteAll();
}
