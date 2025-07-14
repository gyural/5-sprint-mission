package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Message;

public interface MessageService {
	void create(String content, Long channelId, String userId);

	void delete(UUID id);

	void update(UUID id, String newContent);

	void read(UUID id);

	List<Message> readAllByChannelId(UUID channelId);
}
