package com.sprint.mission.discodeit.service.file;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;

public class FileMessageService implements MessageService {

	@Override
	public Message create(String content, UUID channelId, UUID userId) {
		return null;
	}

	@Override
	public void delete(UUID id) {

	}

	@Override
	public void deleteAll() {

	}

	@Override
	public void deleteByChannelId(UUID channelId) {

	}

	@Override
	public void update(UUID id, String newContent) {

	}

	@Override
	public Message read(UUID id) {
		return null;
	}

	@Override
	public List<Message> readAll() {
		return List.of();
	}

	@Override
	public List<Message> readAllByChannelId(UUID channelId) {
		return List.of();
	}
}
