package com.sprint.mission.discodeit.service.jsf;

import static com.sprint.mission.discodeit.domain.dto.ReadChannelResponse.*;
import static com.sprint.mission.discodeit.domain.enums.ChannelType.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.ChannelCreateDTO;
import com.sprint.mission.discodeit.domain.dto.ChannelUpdateDTO;
import com.sprint.mission.discodeit.domain.dto.ReadChannelResponse;
import com.sprint.mission.discodeit.domain.entity.Channel;
import com.sprint.mission.discodeit.domain.enums.ChannelType;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;

public class JCFChannelService implements ChannelService {

	private final JCFChannelRepository channelRepository;
	private final MessageService messageService;

	/**
	 * Constructs a JCFChannelService with the specified message service and channel repository.
	 */
	public JCFChannelService(MessageService messageService, JCFChannelRepository channelRepository) {
		this.messageService = messageService;
		this.channelRepository = channelRepository;
	}

	/**
	 * Creates and saves a new public channel using the provided data.
	 *
	 * @param dto the data transfer object containing the channel's name and description
	 * @return the created public Channel entity
	 * @throws IllegalArgumentException if the channel name or description is null or empty
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
	 * Creates and saves a new private channel using the provided data.
	 *
	 * @param dto Data transfer object containing the channel's name and description.
	 * @return The newly created private Channel entity.
	 * @throws IllegalArgumentException if the channel name or description is null or empty.
	 */
	@Override
	public Channel createPrivate(ChannelCreateDTO dto) {
		String name = dto.getName();
		String description = dto.getDescription();

		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Channel name cannot be null or empty");
		}
		if (description == null || description.isEmpty()) {
			throw new IllegalArgumentException("Channel description cannot be null or empty");
		}

		return channelRepository.save(new Channel(PRIVATE, name, description));
	}

	/**
	 * Retrieves a channel by its unique identifier and returns its details as a {@link ReadChannelResponse}.
	 *
	 * @param id the UUID of the channel to retrieve
	 * @return a {@link ReadChannelResponse} containing the channel's details
	 * @throws NoSuchElementException if no channel with the specified ID exists
	 */
	@Override
	public ReadChannelResponse read(UUID id) {
		Channel channel = channelRepository.find(id)
		  .orElseThrow(() -> new NoSuchElementException("Channel with ID " + id + " not found"));

		return ReadChannelResponse.builder()
		  .id(channel.getId())
		  .createdAt(channel.getCreatedAt())
		  .updatedAt(channel.getUpdatedAt())
		  .channelType(channel.getChannelType())
		  .name(channel.getName())
		  .description(channel.getDescription())
		  .build();
	}

	/**
	 * Retrieves all channels and returns them as a list of {@code ReadChannelResponse} objects.
	 *
	 * @param userId the UUID of the user (currently unused in filtering)
	 * @return a list of all channels represented as {@code ReadChannelResponse} DTOs
	 */
	@Override
	public List<ReadChannelResponse> findAllByUserId(UUID userId) {
		return channelRepository.findAll().stream()
		  .map(c -> toReadChannelResponse(c, c.getCreatedAt(), List.of()))
		  .toList();
	}

	/**
	 * Deletes the channel identified by the given UUID and removes all associated messages.
	 *
	 * @param id the UUID of the channel to delete
	 */
	@Override
	public void delete(UUID id) {
		// 연관된 메시지도 삭제
		messageService.deleteAllByChannelId(id);

		channelRepository.delete(id);

	}

	/**
	 * Updates an existing channel's type, name, and description using the provided update DTO.
	 *
	 * @param dto the data transfer object containing the channel's ID, new type, name, and description
	 * @throws IllegalArgumentException if the ID, name, description, or channel type is null or empty
	 * @throws NoSuchElementException if a channel with the specified ID does not exist
	 */
	@Override
	public void update(ChannelUpdateDTO dto) {
		String newChannelName = dto.getName();
		String newDescription = dto.getDescription();
		ChannelType newChannelType = dto.getChannelType();
		UUID id = dto.getId();

		if (id == null) {
			throw new IllegalArgumentException("Channel ID cannot be null");
		}

		if (newChannelName == null || newChannelName.isEmpty()) {
			throw new IllegalArgumentException("Channel name cannot be null or empty");
		}
		if (newDescription == null || newDescription.isEmpty()) {
			throw new IllegalArgumentException("Channel description cannot be null or empty");
		}
		if (newChannelType == null) {
			throw new IllegalArgumentException("Channel type cannot be null");
		}

		Channel targetChannel = channelRepository.find(id)
		  .orElseThrow(() -> new NoSuchElementException("Channel with ID " + id + " not found"));

		targetChannel.setChannelType(newChannelType);
		targetChannel.setName(newChannelName);
		targetChannel.setDescription(newDescription);

		channelRepository.save(targetChannel);
	}

	@Override
	public boolean isEmpty(UUID id) {
		return channelRepository.isEmpty(id);
	}

	@Override
	public void deleteAll() {
		channelRepository.deleteAll();

		// 연관된 메시지 삭제 (CASCADE DELETE)
		messageService.deleteAll();
	}
}
