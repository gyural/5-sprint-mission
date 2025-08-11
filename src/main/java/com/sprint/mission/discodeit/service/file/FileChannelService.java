package com.sprint.mission.discodeit.service.file;

import static com.sprint.mission.discodeit.domain.enums.ChannelType.*;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.CreateChannelDTO;
import com.sprint.mission.discodeit.domain.dto.UpdateChannelDTO;
import com.sprint.mission.discodeit.domain.entity.Channel;
import com.sprint.mission.discodeit.domain.enums.ChannelType;
import com.sprint.mission.discodeit.domain.response.ReadChannelResponse;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.service.ChannelService;

public class FileChannelService implements ChannelService {

	private final FileChannelRepository channelRepository;
	private final FileMessageRepository messageRepository;

	public FileChannelService(FileChannelRepository channelRepository, FileMessageRepository messageRepository) {
		this.channelRepository = channelRepository;
		this.messageRepository = messageRepository;
	}

	@Override
	public Channel createPublic(CreateChannelDTO dto) {
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

	public Channel createPrivate(CreateChannelDTO dto) {
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

	@Override
	public ReadChannelResponse readPrivate(UUID id) {
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

	@Override
	public ReadChannelResponse readPublic(UUID id) {
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

	@Override
	public List<ReadChannelResponse> findAllByUserId(UUID userId) {
		return channelRepository.findAll().stream()
		  .map(c -> toReadChannelResponse(c, c.getCreatedAt(), List.of()))
		  .toList();
	}

	@Override
	public void delete(UUID id) {
		// 연관된 메시지도 삭제
		messageRepository.deleteByChannelId(id);

		channelRepository.delete(id);

	}

	@Override
	public void update(UpdateChannelDTO dto) {
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

	private ReadChannelResponse toReadChannelResponse(Channel channel, Instant LastMessageAt,
	  List<UUID> membersIDList) {

		return ReadChannelResponse.builder()
		  .id(channel.getId())
		  .createdAt(channel.getCreatedAt())
		  .updatedAt(channel.getUpdatedAt())
		  .channelType(channel.getChannelType())
		  .name(channel.getName())
		  .description(channel.getDescription())
		  .lastMessageAt(LastMessageAt)
		  .membersIDs(membersIDList)
		  .build();
	}
}
