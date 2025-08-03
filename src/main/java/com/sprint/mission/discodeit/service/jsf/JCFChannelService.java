package com.sprint.mission.discodeit.service.jsf;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;

public class JCFChannelService implements ChannelService {

	private final JCFChannelRepository channelRepository;
	private final JCFMessageService messageService;

	public JCFChannelService(JCFMessageService messageService, JCFChannelRepository channelRepository) {
		this.messageService = messageService;
		this.channelRepository = channelRepository;
	}

	@Override
	public void create(String name, String description) {
		channelRepository.create(name, description);
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
		channelRepository.delete(id);

		// 연관된 메시지도 삭제
		messageService.deleteByChannelId(id);
	}

	@Override
	public void update(UUID id, String newChannelName, String newDescription) {

		channelRepository.update(id, newChannelName, newDescription);
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
