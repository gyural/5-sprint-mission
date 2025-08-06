package com.sprint.mission.discodeit.service.basic;

import static com.sprint.mission.discodeit.domain.dto.ReadChannelResponse.*;
import static com.sprint.mission.discodeit.domain.enums.ChannelType.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sprint.mission.discodeit.domain.dto.ChannelCreateDTO;
import com.sprint.mission.discodeit.domain.dto.ChannelUpdateDTO;
import com.sprint.mission.discodeit.domain.dto.ReadChannelResponse;
import com.sprint.mission.discodeit.domain.entity.Channel;
import com.sprint.mission.discodeit.domain.entity.Message;
import com.sprint.mission.discodeit.domain.entity.ReadStatus;
import com.sprint.mission.discodeit.domain.enums.ChannelType;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {

	private final ChannelRepository channelRepository;
	private final MessageRepository messageRepository;
	private final ReadStatusRepository readStatusRepository;

	/**
	 * Creates and saves a new public channel using the provided name and description.
	 *
	 * @param dto Data transfer object containing the channel's name and description.
	 * @return The newly created public channel entity.
	 * @throws IllegalArgumentException if the name or description is null or empty.
	 */
	@Override
	public Channel createPublic(ChannelCreateDTO dto) {
		String name = dto.getName();
		String description = dto.getDescription();

		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Channel name cannot be null or empty");
		}
		if (description == null || description.isEmpty()) {
			throw new IllegalArgumentException("Channel description cannot be null or empty");
		}

		return channelRepository.save(new Channel(PUBLIC, name, description));
	}

	/**
	 * Creates a new private channel with the specified members.
	 *
	 * The created channel has no name or description. For each member in the provided DTO,
	 * a corresponding read status entry is initialized and saved.
	 *
	 * @param dto the data transfer object containing the list of members to include in the private channel
	 * @return the newly created private channel entity
	 */
	@Override
	public Channel createPrivate(ChannelCreateDTO dto) {

		Channel newChannel = new Channel(PRIVATE, null, null);

		dto.getMembers().stream().forEach(
		  member -> readStatusRepository.save(
			new ReadStatus(member.getId(), newChannel.getId()))
		);

		return channelRepository.save(newChannel);
	}

	/**
	 * Retrieves detailed information about a channel by its ID.
	 *
	 * If the channel is private, includes the list of member user IDs; otherwise, the member list is empty.
	 * Also provides the timestamp of the most recent message edit or creation, or null if there are no messages.
	 *
	 * @param id the unique identifier of the channel to retrieve
	 * @return a {@link ReadChannelResponse} containing channel details, last message timestamp, and member user IDs
	 * @throws NoSuchElementException if the channel with the specified ID does not exist
	 */
	@Override
	public ReadChannelResponse read(UUID id) {
		Channel channel = channelRepository.find(id)
		  .orElseThrow(() -> new NoSuchElementException("Channel with ID " + id + " not found"));

		// 1. 가장 최근 메시지 가 언제인지 확인
		List<Message> messages = messageRepository.findAllByChannelId(id);
		Instant lastMessageAt = messages.isEmpty() ? null
		  : getLastEditAt(messages);

		List<UUID> membersIDList = channel.getChannelType() == PRIVATE ?
		  readStatusRepository.findAllByChannelId(id)
			.stream()
			.map(ReadStatus::getUserId)
			.toList()
		  : new ArrayList<>();

		return toReadChannelResponse(channel, lastMessageAt, membersIDList);
	}

	/**
	 * Retrieves all channels visible to the specified user, including all public channels and private channels where the user is a member.
	 *
	 * For each channel, returns a response containing channel details, the timestamp of the most recent message (if any), and the list of member user IDs for private channels.
	 *
	 * @param userId the ID of the user whose accessible channels are to be retrieved
	 * @return a list of channel response objects with message and membership information
	 */
	@Override
	public List<ReadChannelResponse> findAllByUserId(UUID userId) {
		return channelRepository.findAll().stream()
		  // 필터링: PUBLIC 채널 또는 사용자가 참여한 PRIVATE 채널
		  .filter(c -> c.getChannelType() == PUBLIC ||
			readStatusRepository.findAllByUserId(userId)
			  .stream()
			  .anyMatch(us -> us.getChannelId().equals(c.getId())))
		  // 각 채널에 대해 메시지와 멤버 정보를 포함한 ReadChannelResponse 생성
		  .map(c -> {
				messageRepository.findAllByChannelId(c.getId());
				List<Message> messages = messageRepository.findAllByChannelId(c.getId());
				Instant lastMessageAt = messages.isEmpty() ? null
				  : getLastEditAt(messages);

				List<UUID> membersIDList = c.getChannelType() == PRIVATE ?
				  readStatusRepository.findAllByChannelId(c.getId())
					.stream()
					.map(ReadStatus::getUserId)
					.toList()
				  : new ArrayList<>();

				return toReadChannelResponse(c, lastMessageAt, membersIDList);
			}
		  )
		  .toList();

	}

	/**
	 * Deletes the channel with the specified ID, along with all associated messages and read status entries.
	 *
	 * @param id the unique identifier of the channel to delete
	 */
	@Override
	public void delete(UUID id) {
		// 연관된 메시지도 삭제
		messageRepository.deleteByChannelId(id);
		// 연관된 유저 상태도 삭제
		readStatusRepository.deleteByChannelId(id);

		channelRepository.delete(id);

	}

	/**
	 * Updates the type, name, and description of a public channel using the provided update DTO.
	 *
	 * @param dto the data transfer object containing the channel ID, new name, new description, and new channel type
	 * @throws IllegalArgumentException if the DTO, name, description, or channel type is null or empty, or if the channel does not exist
	 * @throws RuntimeException if attempting to update a private channel
	 */
	@Override
	public void update(ChannelUpdateDTO dto) {
		Optional.ofNullable(dto).orElseThrow(() -> new IllegalArgumentException("ChannelUpdateDTO cannot be null"));

		UUID id = dto.getId();
		String newChannelName = dto.getName();
		String newDescription = dto.getDescription();
		ChannelType newChannelType = dto.getChannelType();

		if (newChannelName == null || newChannelName.isEmpty()) {
			throw new IllegalArgumentException("Channel name cannot be null or empty");
		}
		if (newDescription == null || newDescription.isEmpty()) {
			throw new IllegalArgumentException("Channel description cannot be null or empty");
		}
		if (newChannelType == null) {
			throw new IllegalArgumentException("Channel type cannot be null");
		}

		// 채널이 존재하는지 확인
		Channel targetChannel = channelRepository.find(id)
		  .orElseThrow(() -> new IllegalArgumentException("Channel with ID " + id + " not found"));

		if (targetChannel.getChannelType() == PRIVATE) {
			throw new RuntimeException(
			  "Cannot update a private channel's type or members directly. Please use the appropriate service method.");
		}

		targetChannel.setChannelType(newChannelType);
		targetChannel.setName(newChannelName);
		targetChannel.setDescription(newDescription);

		channelRepository.save(targetChannel);
	}

	@Override
	public boolean isEmpty(UUID id) {
		return channelRepository.isEmpty(id);
	}

	/**
	 * Deletes all channels and all messages from the system.
	 */
	@Override
	public void deleteAll() {
		channelRepository.deleteAll();
		messageRepository.deleteAll();
	}

	/**
	 * Returns the latest edit or creation timestamp among a list of messages.
	 *
	 * @param messages the list of messages to evaluate
	 * @return the most recent edit or creation time found in the messages
	 * @throws NoSuchElementException if the message list is empty
	 */
	private Instant getLastEditAt(List<Message> messages) {
		return messages.stream().map(this::getMessageLastEditAt)
		  .max(Instant::compareTo)
		  .orElseThrow(() -> new NoSuchElementException("No messages found"));
	}

	/**
	 * Returns the most recent timestamp for a message, using the updated time if available, otherwise the created time.
	 *
	 * @param message the message whose timestamp is to be determined
	 * @return the latest edit or creation timestamp of the message
	 */
	private Instant getMessageLastEditAt(Message message) {
		return message.getUpdatedAt() != null ? message.getUpdatedAt() : message.getCreatedAt();
	}

}
