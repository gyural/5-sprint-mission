package com.sprint.mission.discodeit.service.jsf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;

public class JCFMessageService implements MessageService {
	public final Map<UUID, Message> data;
	private final JCFUserService userService;

	public JCFMessageService(JCFUserService userService) {
		data = new HashMap<>();
		this.userService = userService;
	}

	@Override
	public void create(String content, UUID channelId, UUID userId) {
		if (content == null || content.isEmpty()) {
			throw new IllegalArgumentException("Content cannot be null or empty");
		}

		if (userId == null || userService.isEmpty(userId)) {
			throw new IllegalArgumentException("User ID cannot be null or empty");
		}
		Message newMessage = new Message(content, userId, channelId, userService.read(userId).getUsername());
		data.put(newMessage.getId(), newMessage);
	}

	@Override
	public Message read(UUID id) {
		if (!data.containsKey(id)) {
			throw new IllegalArgumentException("Message with ID " + id + " not found");
		}

		return data.get(id);

	}

	@Override
	public List<Message> readAll() {
		return data.values().stream().toList();
	}

	@Override
	public void delete(UUID id) {
		if (!data.containsKey(id)) {
			throw new IllegalArgumentException("Message with ID " + id + " not found");
		}

		data.remove(id);
	}

	@Override
	public void deleteAll() {
		data.clear();
	}

	@Override
	public void deleteByChannelId(UUID channelId) {
		if (channelId == null) {
			throw new IllegalArgumentException("Channel ID cannot be null or empty");
		}

		List<Message> messagesToDelete = readAllByChannelId(channelId);
		for (Message message : messagesToDelete) {
			delete(message.getId());
		}
	}

	@Override
	public void update(UUID id, String newContent) {
		if (!data.containsKey(id)) {
			throw new IllegalArgumentException("Message with ID " + id + " not found");
		}

		if (newContent == null || newContent.isEmpty()) {
			throw new IllegalArgumentException("New content cannot be null or empty");
		}

		Message message = data.get(id);
		message.setContent(newContent);
		message.setUpdatedAt(System.currentTimeMillis());
	}

	@Override
	public List<Message> readAllByChannelId(UUID channelId) {
		return data.values().stream()
		  .filter(message -> message.getChannelId().equals(channelId)).toList();
	}

	@Override
	public String toString() {
		return "JCFMessageService{" +
		  "data=" + data +
		  ", userService=" + userService +
		  '}';
	}
}
