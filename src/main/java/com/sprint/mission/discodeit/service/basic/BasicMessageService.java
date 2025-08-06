package com.sprint.mission.discodeit.service.basic;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

public class BasicMessageService implements MessageService {

	private final MessageRepository messageRepository;
	private final UserService userService;
	private final ChannelRepository channelRepository;

	public BasicMessageService(MessageRepository messageRepository, UserService userService,
	  ChannelRepository channelRepository) {
		this.messageRepository = messageRepository;
		this.userService = userService;
		this.channelRepository = channelRepository;
	}

	@Override
	public Message create(String content, UUID channelId, UUID userId) {

		if (content == null || content.isEmpty()) {
			throw new IllegalArgumentException("Content cannot be null or empty");
		}
		if (channelId == null || channelRepository.isEmpty(channelId)) {
			throw new IllegalArgumentException("Channel ID cannot be null or empty");
		}
		if (userId == null || userService.isEmpty(userId)) {
			throw new IllegalArgumentException("User ID cannot be null or empty");
		}

		return messageRepository.save(new Message(content, channelId, userId));
	}

	@Override
	public void delete(UUID id) {
		messageRepository.delete(id);
	}

	@Override
	public void deleteAll() {
		messageRepository.deleteAll();
	}

	@Override
	public void deleteAllByChannelId(UUID channelId) {
		if (channelRepository.isEmpty(channelId)) {
			throw new IllegalArgumentException("Channel ID cannot be null or empty");
		}
		messageRepository.deleteByChannelId(channelId);
	}

	@Override
	public void update(UUID id, String newContent) {
		if (newContent == null || newContent.isEmpty()) {
			throw new IllegalArgumentException("New content cannot be null or empty");
		}

		Message targetMessage = messageRepository.find(id)
		  .orElseThrow(() -> new IllegalArgumentException("Message with ID " + id + " not found"));
		targetMessage.setContent(newContent);

		messageRepository.save(targetMessage);
	}

	@Override
	public Message read(UUID id) {
		return messageRepository.find(id)
		  .orElseThrow(() -> new NoSuchElementException("Message with ID " + id + " not found"));
	}

	@Override
	public List<Message> readAll() {
		return messageRepository.findAll();
	}

	@Override
	public List<Message> readAllByChannelId(UUID channelId) {
		return messageRepository.findAllByChannelId(channelId);
	}

	@Override
	public boolean isEmpty(UUID channelId) {
		return messageRepository.isEmpty(channelId);
	}
}
