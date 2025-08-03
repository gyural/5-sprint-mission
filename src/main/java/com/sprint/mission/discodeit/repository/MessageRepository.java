package com.sprint.mission.discodeit.repository;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Message;

public interface MessageRepository {
	Message create(String content, UUID channelId, UUID userId);

	void delete(UUID id);

	void deleteAll();

	void deleteByChannelId(UUID channelId);

	void update(UUID id, String newContent);

	Message find(UUID id);

	List<Message> findAll();

	List<Message> findAllByChannelId(UUID channelId);
}
