package com.sprint.mission.discodeit.service.jsf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;

public class JCFMessageService implements MessageService {
	private static final Map<UUID, Message> data = new HashMap<>();
	private final JCFUserService userService = new JCFUserService();
	private final JCFChannelService channelService = new JCFChannelService();

	@Override
	public void create(String content, UUID channelId, UUID userId) {
		if (content == null || content.isEmpty()) {
			throw new IllegalArgumentException("Content cannot be null or empty");
		}
		if (channelId == null || channelService.isEmpty(channelId)) {
			throw new IllegalArgumentException("Channel ID cannot be null or empty");
		}
		if (userId == null || userService.isEmpty(userId)) {
			throw new IllegalArgumentException("User ID cannot be null or empty");
		}

		Message newMessage = new Message(content, channelId, userId, userService.read(userId).getUsername());
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
	public void delete(UUID id) {
		if (!data.containsKey(id)) {
			throw new IllegalArgumentException("Message with ID " + id + " not found");
		}

		data.remove(id);
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

}
