package com.sprint.mission.discodeit.service.jsf;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
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
	public Channel create(ChannelType channelType, String name, String description) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Channel name cannot be null or empty");
		}
		if (description == null || description.isEmpty()) {
			throw new IllegalArgumentException("Channel description cannot be null or empty");
		}
		if (channelType == null) {
			throw new IllegalArgumentException("Channel type cannot be null");
		}

		return channelRepository.create(channelType, name, description);
	}

	@Override
	public Channel read(UUID id) {
		return channelRepository.find(id);
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
	public void update(UUID id, ChannelType newChannelType, String newChannelName, String newDescription) {
		if (newChannelName == null || newChannelName.isEmpty()) {
			throw new IllegalArgumentException("Channel name cannot be null or empty");
		}
		if (newDescription == null || newDescription.isEmpty()) {
			throw new IllegalArgumentException("Channel description cannot be null or empty");
		}
		if (newChannelType == null) {
			throw new IllegalArgumentException("Channel type cannot be null");
		}

		channelRepository.update(id, newChannelType, newChannelName, newDescription);
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
