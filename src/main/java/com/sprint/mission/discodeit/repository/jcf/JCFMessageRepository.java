package com.sprint.mission.discodeit.repository.jcf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

public class JCFMessageRepository implements MessageRepository {

	public final Map<UUID, Message> data;

	public JCFMessageRepository() {
		this.data = new HashMap<>();
	}

	@Override
	public Message create(String content, UUID channelId, UUID userId) {
		Message newMessage = new Message(content, channelId, userId);
		data.put(newMessage.getId(), newMessage);
		return newMessage;
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
		if (data.isEmpty()) {
			return;
			// throw new IllegalArgumentException("No messages to delete");
		}
		data.clear();
	}

	@Override
	public void deleteByChannelId(UUID channelId) {
		data.values().removeIf(message -> message.getChannelId().equals(channelId));
	}

	@Override
	public void update(UUID id, String newContent) {
		if (!data.containsKey(id)) {
			throw new IllegalArgumentException("Message with ID " + id + " not found");
		}
		data.get(id).setContent(newContent);
	}

	@Override
	public Message find(UUID id) {
		if (!data.containsKey(id)) {
			throw new IllegalArgumentException("Message with ID " + id + " not found");
		}
		return data.get(id);
	}

	@Override
	public List<Message> findAll() {
		return data.values().stream().toList();
	}

	@Override
	public List<Message> findAllByChannelId(UUID channelId) {
		return data.values().stream()
		  .filter(message -> message.getChannelId().equals(channelId))
		  .toList();
	}

	public boolean isEmpty(UUID id) {
		return !data.containsKey(id);
	}
}
