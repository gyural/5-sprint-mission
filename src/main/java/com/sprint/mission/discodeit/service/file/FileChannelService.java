package com.sprint.mission.discodeit.service.file;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.ChannelCreateDTO;
import com.sprint.mission.discodeit.domain.dto.ChannelUpdateDTO;
import com.sprint.mission.discodeit.domain.entity.Channel;
import com.sprint.mission.discodeit.domain.entity.ChannelType;
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
	public Channel create(ChannelCreateDTO dto) {
		String name = dto.getName();
		String description = dto.getDescription();
		ChannelType channelType = dto.getChannelType();

		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Channel name cannot be null or empty");
		}
		if (description == null || description.isEmpty()) {
			throw new IllegalArgumentException("Channel description cannot be null or empty");
		}
		if (channelType == null) {
			throw new IllegalArgumentException("Channel type cannot be null");
		}
		return channelRepository.save(new Channel(channelType, name, description));
	}

	@Override
	public Channel read(UUID id) {
		return channelRepository.find(id).orElseThrow(()
		  -> new NoSuchElementException("Channel with ID " + id + " not found"));
	}

	@Override
	public List<Channel> readAll() {
		return channelRepository.findAll();
	}

	@Override
	public void delete(UUID id) {
		// 연관된 메시지도 삭제
		messageRepository.deleteByChannelId(id);

		channelRepository.delete(id);

	}

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
