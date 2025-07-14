package com.sprint.mission.discodeit.service.jsf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;

public class JCFMessageService implements MessageService {
	private static final Map<String, Message> data = new HashMap<>();

	@Override
	public void create(String content, Long channelId, String userId) {
		// Implementation for creating a message
	}

	@Override
	public void delete(UUID id) {
		if (!data.containsKey(id.toString())) {
			throw new IllegalArgumentException("Message with ID " + id + " not found");
		}
		data.remove(id.toString());
	}

	@Override
	public void update(UUID id, String newContent) {
		// Implementation for updating a message
	}

	@Override
	public void read(UUID id) {
		// Implementation for reading a message
	}

	@Override
	public List<Message> readAllByChannelId(UUID channelId) {
		return data.values().stream()
		  .filter(message -> message.getChannelId().equals(channelId)).toList();
	}

}
