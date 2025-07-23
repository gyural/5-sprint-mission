package com.sprint.mission.discodeit.service.file;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.service.MessageService;

public class FileMessageService implements MessageService {

	private final FileMessageRepository messageRepository;
	private final FileUserService userService;
	private final FileChannelRepository channelRepository;

	public FileMessageService(FileMessageRepository messageRepository, FileUserService userService,
	  FileChannelRepository channelRepository) {
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

		return messageRepository.create(content, channelId, userId);
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
	public void deleteByChannelId(UUID channelId) {
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

		messageRepository.update(id, newContent);
	}

	@Override
	public Message read(UUID id) {
		return messageRepository.find(id);
	}

	@Override
	public List<Message> readAll() {
		return messageRepository.findAll();
	}

	@Override
	public List<Message> readAllByChannelId(UUID channelId) {
		return messageRepository.findAllByChannelId(channelId);
	}
}
