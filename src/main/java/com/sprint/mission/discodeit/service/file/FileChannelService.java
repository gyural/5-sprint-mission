package com.sprint.mission.discodeit.service.file;

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
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.service.ChannelService;

public class FileChannelService implements ChannelService {

	private final FileChannelRepository channelRepository;
	private final FileMessageRepository messageRepository;

	/**
	 * Constructs a FileChannelService with the specified channel and message repositories.
	 *
	 * @param channelRepository the repository used for channel data persistence
	 * @param messageRepository the repository used for message data persistence
	 */
	public FileChannelService(FileChannelRepository channelRepository, FileMessageRepository messageRepository) {
		this.channelRepository = channelRepository;
		this.messageRepository = messageRepository;
	}

	/**
	 * Creates and persists a new public channel using the provided data.
	 *
	 * @param dto Data transfer object containing the channel's name and description.
	 * @return The newly created public Channel entity.
	 * @throws IllegalArgumentException if the channel name or description is null or empty.
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
	 * Creates a new private channel with the specified name and description.
	 *
	 * @param dto Data transfer object containing the channel's name and description.
	 * @return The created private Channel entity.
	 * @throws IllegalArgumentException if the name or description is null or empty.
	 */
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
	 * Retrieves a channel by its unique identifier and returns its details as a response DTO.
	 *
	 * @param id the UUID of the channel to retrieve
	 * @return a {@code ReadChannelResponse} containing the channel's details
	 * @throws NoSuchElementException if no channel with the specified ID exists
	 */
	@Override
	public ReadChannelResponse read(UUID id) {
		Channel channel = channelRepository.find(id)
		  .orElseThrow(() -> new NoSuchElementException("Channel with ID " + id + " not found"));

		return ReadChannelResponse.builder()
		  .id(channel.getId())
		  .name(channel.getName())
		  .description(channel.getDescription())
		  .channelType(channel.getChannelType())
		  .createdAt(channel.getCreatedAt())
		  .updatedAt(channel.getUpdatedAt())
		  .build();
	}

	/**
	 * Retrieves all channels and returns them as a list of {@code ReadChannelResponse} objects.
	 *
	 * @param userId the user ID (currently unused in filtering)
	 * @return a list of channel responses representing all channels
	 */
	@Override
	public List<ReadChannelResponse> findAllByUserId(UUID userId) {
		return channelRepository.findAll().stream()
		  .map(c -> toReadChannelResponse(c, c.getCreatedAt(), List.of()))
		  .toList();
	}

	/**
	 * Deletes the channel with the specified UUID and all messages associated with it.
	 *
	 * @param id the UUID of the channel to delete
	 */
	@Override
	public void delete(UUID id) {
		// 연관된 메시지도 삭제
		messageRepository.deleteByChannelId(id);

		channelRepository.delete(id);

	}

	/**
	 * Updates an existing channel's type, name, and description using the provided update DTO.
	 *
	 * @param dto the data transfer object containing the channel ID, new type, name, and description
	 * @throws IllegalArgumentException if the channel ID, name, description, or type is null or empty
	 * @throws NoSuchElementException if the channel with the specified ID does not exist
	 */
	@Override
	public void update(ChannelUpdateDTO dto) {
		UUID id = dto.getId();
		ChannelType newChannelType = dto.getChannelType();
		String newChannelName = dto.getName();
		String newDescription = dto.getDescription();

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
		Channel channel = channelRepository.find(id).orElseThrow(()
		  -> new NoSuchElementException("Channel with ID " + id + " not found"));
		channel.setChannelType(newChannelType);
		channel.setName(newChannelName);
		channel.setDescription(newDescription);

		channelRepository.save(channel);
	}

	@Override
	public boolean isEmpty(UUID id) {
		return channelRepository.isEmpty(id);
	}

	@Override
	public void deleteAll() {
		channelRepository.deleteAll();
		messageRepository.deleteAll();
	}
}
