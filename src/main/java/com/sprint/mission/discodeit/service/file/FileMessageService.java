package com.sprint.mission.discodeit.service.file;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.MessageCreateDTO;
import com.sprint.mission.discodeit.domain.dto.MessageUpdateDTO;
import com.sprint.mission.discodeit.domain.entity.Message;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

public class FileMessageService implements MessageService {

	private final FileMessageRepository messageRepository;
	private final UserService userService;
	private final FileChannelRepository channelRepository;

	/**
	 * Constructs a FileMessageService with the specified message repository, user service, and channel repository.
	 */
	public FileMessageService(FileMessageRepository messageRepository, UserService userService,
	  FileChannelRepository channelRepository) {
		this.messageRepository = messageRepository;
		this.userService = userService;
		this.channelRepository = channelRepository;
	}

	/**
	 * Creates and saves a new message using the provided data transfer object.
	 *
	 * @param dto the data transfer object containing message content, channel ID, and user ID
	 * @return the saved Message entity
	 * @throws IllegalArgumentException if the DTO, content, channel ID, or user ID is null or invalid
	 */
	@Override
	public Message create(MessageCreateDTO dto) {
		Optional.ofNullable(dto).orElseThrow(() -> new IllegalArgumentException("MessageCreateDTO cannot be null"));

		String content = dto.getContent();
		UUID channelId = dto.getChannelId();
		UUID userId = dto.getUserId();

		if (content == null || content.isEmpty()) {
			throw new IllegalArgumentException("Content cannot be null or empty");
		}
		if (channelId == null || channelRepository.isEmpty(channelId)) {
			throw new IllegalArgumentException("Channel ID cannot be null or empty");
		}
		if (userId == null || userService.isEmpty(userId)) {
			throw new IllegalArgumentException("User ID cannot be null or empty");
		}

		return messageRepository.save(new Message(content, channelId, userId, null));
	}

	/**
	 * Deletes the message with the specified ID from the repository.
	 *
	 * @param id the unique identifier of the message to delete
	 */
	@Override
	public void delete(UUID id) {
		messageRepository.delete(id);
	}

	@Override
	public void deleteAll() {
		messageRepository.deleteAll();
	}

	/**
	 * Deletes all messages associated with the specified channel ID.
	 *
	 * @param channelId the unique identifier of the channel whose messages will be deleted
	 * @throws IllegalArgumentException if the channel ID is null, empty, or does not exist
	 */
	@Override
	public void deleteAllByChannelId(UUID channelId) {
		if (channelRepository.isEmpty(channelId)) {
			throw new IllegalArgumentException("Channel ID cannot be null or empty");
		}
		messageRepository.deleteByChannelId(channelId);
	}

	/**
	 * Updates the content of an existing message using data from the provided DTO.
	 *
	 * @param dto the data transfer object containing the message ID and new content
	 * @throws IllegalArgumentException if the DTO is null or the new content is null or empty
	 * @throws NoSuchElementException if a message with the specified ID does not exist
	 */
	@Override
	public void update(MessageUpdateDTO dto) {
		Optional.ofNullable(dto).orElseThrow(() -> new IllegalArgumentException("MessageUpdateDTO cannot be null"));
		UUID id = dto.getId();
		String newContent = dto.getNewContent();

		if (newContent == null || newContent.isEmpty()) {
			throw new IllegalArgumentException("New content cannot be null or empty");
		}

		Message targetMessage = messageRepository.find(id)
		  .orElseThrow(() -> new NoSuchElementException("Message with ID " + id + " not found"));
		targetMessage.setContent(newContent);
		messageRepository.save(targetMessage);
	}

	/**
	 * Retrieves a message by its unique identifier.
	 *
	 * @param id the unique identifier of the message to retrieve
	 * @return the message with the specified ID
	 * @throws NoSuchElementException if no message with the given ID exists
	 */
	@Override
	public Message read(UUID id) {
		return messageRepository.find(id)
		  .orElseThrow(() -> new NoSuchElementException("Message with ID " + id + " not found"));
	}

	/**
	 * Retrieves all messages associated with the specified channel ID.
	 *
	 * @param channelId the unique identifier of the channel
	 * @return a list of messages belonging to the given channel
	 */
	@Override
	public List<Message> findAllByChannelId(UUID channelId) {
		return messageRepository.findAll().stream().filter(
			message -> message.getChannelId().equals(channelId))
		  .toList();
	}

	/**
	 * Retrieves all messages associated with the specified channel ID.
	 *
	 * @param channelId the unique identifier of the channel
	 * @return a list of messages belonging to the given channel
	 */
	@Override
	public List<Message> readAllByChannelId(UUID channelId) {
		return messageRepository.findAllByChannelId(channelId);
	}

	@Override
	public boolean isEmpty(UUID channelId) {
		return messageRepository.isEmpty(channelId);
	}
}
