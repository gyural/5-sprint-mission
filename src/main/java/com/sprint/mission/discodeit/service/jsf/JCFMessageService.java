package com.sprint.mission.discodeit.service.jsf;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.MessageCreateDTO;
import com.sprint.mission.discodeit.domain.dto.MessageUpdateDTO;
import com.sprint.mission.discodeit.domain.entity.Message;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

public class JCFMessageService implements MessageService {

	private final JCFMessageRepository messageRepository;
	private final JCFChannelRepository channelRepository;

	private final UserService userService;
	/**
	 * Constructs a JCFMessageService with the specified message repository, channel repository, and user service.
	 */

	public JCFMessageService(JCFMessageRepository messageRepository, JCFChannelRepository channelRepository,
	  UserService userService) {
		this.messageRepository = messageRepository;
		this.channelRepository = channelRepository;
		this.userService = userService;
	}

	/**
	 * Creates and saves a new message using the provided data transfer object.
	 *
	 * @param dto the data transfer object containing message content, channel ID, user ID, and author name
	 * @return the saved Message entity
	 * @throws IllegalArgumentException if the DTO, content, user ID, or channel ID is null or invalid
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

		if (userId == null || userService.isEmpty(userId)) {
			throw new IllegalArgumentException("User ID cannot be null or empty");
		}

		if (channelId == null || channelRepository.isEmpty(channelId)) {
			throw new IllegalArgumentException("Channel ID cannot be null or empty");
		}

		// 1. 데이터 저장
		// 2. 채널에 참여한 사용자들에게 알림을 전송
		// messageAlarmService.sendMessageAlarm(newMessage);

		return messageRepository.save(new Message(content, channelId, userId, dto.getAuthorName()));
	}

	/**
	 * Retrieves a message by its unique identifier.
	 *
	 * @param id the unique identifier of the message to retrieve
	 * @return the message with the specified ID
	 * @throws NoSuchElementException if no message is found with the given ID
	 */
	@Override
	public Message read(UUID id) {
		return messageRepository.find(id)
		  .orElseThrow(() -> new NoSuchElementException("Message not found with ID: " + id));
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
	 * Deletes a message by its unique identifier.
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
	 * @throws IllegalArgumentException if the channel ID is null or does not exist
	 */
	@Override
	public void deleteAllByChannelId(UUID channelId) {
		if (channelId == null || channelRepository.isEmpty(channelId)) {
			throw new IllegalArgumentException("Channel ID cannot be null or empty");
		}
		// 채널에 속한 모든 메시지를 삭제
		messageRepository.deleteByChannelId(channelId);
	}

	/**
	 * Updates the content of an existing message using the provided update DTO.
	 *
	 * @param dto the data transfer object containing the message ID and new content
	 * @throws IllegalArgumentException if the DTO, message ID, or new content is null or empty, or if the message does not exist
	 * @throws NoSuchElementException if the message with the specified ID is not found
	 */
	@Override
	public void update(MessageUpdateDTO dto) {
		Optional.ofNullable(dto).orElseThrow(() -> new IllegalArgumentException("MessageUpdateDTO cannot be null"));
		UUID id = dto.getId();
		String newContent = dto.getNewContent();

		if (newContent == null || newContent.isEmpty()) {
			throw new IllegalArgumentException("Content cannot be null or empty");
		}

		if (id == null || messageRepository.isEmpty(id)) {
			throw new IllegalArgumentException("Message ID cannot be null or empty");
		}

		Message targetMessage = messageRepository.find(id)
		  .orElseThrow(() -> new NoSuchElementException("Message not found with ID: " + id));
		targetMessage.setContent(newContent);

		// 메시지 내용 수정
		messageRepository.save(targetMessage);
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
