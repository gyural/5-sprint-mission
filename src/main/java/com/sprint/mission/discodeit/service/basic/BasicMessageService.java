package com.sprint.mission.discodeit.service.basic;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sprint.mission.discodeit.domain.dto.MessageCreateDTO;
import com.sprint.mission.discodeit.domain.dto.MessageUpdateDTO;
import com.sprint.mission.discodeit.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.domain.entity.Message;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {

	private final MessageRepository messageRepository;
	private final UserService userService;
	private final ChannelRepository channelRepository;
	private final BinaryContentRepository binaryContentRepository;

	/**
	 * Creates and saves a new message with the specified content, channel, user, author name, and attachments.
	 *
	 * Validates the input DTO and its fields, ensuring content, channelId, userId, and attachments are present and valid.
	 * Saves all valid attachments and associates their IDs with the new message.
	 *
	 * @param dto the data transfer object containing message content, channel ID, user ID, author name, and attachments
	 * @return the created and saved Message entity
	 * @throws IllegalArgumentException if the DTO or any required field is null or empty, or if any attachment is invalid
	 */
	@Override
	public Message create(MessageCreateDTO dto) {
		Optional.ofNullable(dto).orElseThrow(() -> new IllegalArgumentException("MessageCreateDTO cannot be null"));
		String content = dto.getContent();
		UUID channelId = dto.getChannelId();
		UUID userId = dto.getUserId();
		List<BinaryContent> attachments = dto.getAttachments() != null ? dto.getAttachments() : List.of();

		// Validate inputs
		if (content == null || content.isEmpty()) {
			throw new IllegalArgumentException("Content cannot be null or empty");
		}
		if (channelId == null || channelRepository.isEmpty(channelId)) {
			throw new IllegalArgumentException("Channel ID cannot be null or empty");
		}
		if (userId == null || userService.isEmpty(userId)) {
			throw new IllegalArgumentException("User ID cannot be null or empty");
		}
		attachments.forEach(attachment -> {
			if (attachment == null || attachment.getContent() == null || attachment.getContent().length == 0) {
				throw new IllegalArgumentException("Attachment content cannot be null or empty");
			}
		});

		binaryContentRepository.saveAll(attachments);

		List<UUID> attachmentIds = attachments.stream()
		  .map(BinaryContent::getId)
		  .toList();

		return messageRepository.save(new Message(content, userId, channelId, dto.getAuthorName(), attachmentIds));
	}

	/**
	 * Deletes a message by its ID, including all associated attachments.
	 *
	 * @param id the unique identifier of the message to delete
	 * @throws NoSuchElementException if no message with the specified ID exists
	 */
	@Override
	public void delete(UUID id) {
		Message messageToDelete = messageRepository.find(id)
		  .orElseThrow(() -> new NoSuchElementException("Message with ID " + id + " not found"));

		// 메시지 관련 Attachment 도 삭제
		messageToDelete.getAttachmentIds().forEach(binaryContentRepository::delete);

		// 메시지 삭제
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
	 * @throws IllegalArgumentException if the channel ID is null or does not exist
	 */
	@Override
	public void deleteAllByChannelId(UUID channelId) {
		if (channelRepository.isEmpty(channelId)) {
			throw new IllegalArgumentException("Channel ID cannot be null or empty");
		}
		messageRepository.deleteByChannelId(channelId);
	}

	/**
	 * Updates the content of an existing message using the provided update DTO.
	 *
	 * @param dto the data transfer object containing the message ID and new content
	 * @throws IllegalArgumentException if the DTO is null, the new content is null or empty, or the message is not found
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
		  .orElseThrow(() -> new IllegalArgumentException("Message with ID " + id + " not found"));
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
	 * @param channelId the unique identifier of the channel to filter messages by
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
