package com.sprint.mission.discodeit.service.jsf;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.ChannelCreateDTO;
import com.sprint.mission.discodeit.domain.dto.ChannelUpdateDTO;
import com.sprint.mission.discodeit.domain.entity.Channel;
import com.sprint.mission.discodeit.domain.entity.ChannelType;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;

public class JCFChannelService implements ChannelService {

	private final JCFChannelRepository channelRepository;
	private final MessageService messageService;

	public JCFChannelService(MessageService messageService, JCFChannelRepository channelRepository) {
		this.messageService = messageService;
		this.channelRepository = channelRepository;
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
		return channelRepository.find(id)
		  .orElseThrow(() -> new NoSuchElementException("Channel with ID " + id + " not found"));
	}

	@Override
	public List<Channel> readAll() {
		return channelRepository.findAll();
	}

	@Override
	public void delete(UUID id) {
		// 연관된 메시지도 삭제
		messageService.deleteAllByChannelId(id);

		channelRepository.delete(id);

	}

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
