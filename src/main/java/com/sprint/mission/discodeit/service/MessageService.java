package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Message;

public interface MessageService {
	Message create(String content, UUID channelId, UUID userId);

	void delete(UUID id);

	void deleteAll();

	void deleteByChannelId(UUID channelId);

	void update(UUID id, String newContent);

	Message read(UUID id);

	List<Message> readAll();

	List<Message> readAllByChannelId(UUID channelId);

}
