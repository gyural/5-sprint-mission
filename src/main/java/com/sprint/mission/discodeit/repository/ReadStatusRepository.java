package com.sprint.mission.discodeit.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.entity.ReadStatus;

public interface ReadStatusRepository {
	public ReadStatus save(ReadStatus readStatus);

	public Optional<ReadStatus> find(UUID id);

	public Optional<ReadStatus> findByUserIdAndChannelId(UUID userId, UUID channelId);

	public List<ReadStatus> findAllByUserId(UUID userId);

	public List<ReadStatus> findAllByChannelId(UUID channelId);

	public List<ReadStatus> findAll();

	public void delete(UUID id);

	boolean isEmpty(UUID id);

	public void deleteByChannelId(UUID channelId);

	void deleteAll();

}
