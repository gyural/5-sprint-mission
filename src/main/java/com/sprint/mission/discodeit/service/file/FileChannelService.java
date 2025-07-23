package com.sprint.mission.discodeit.service.file;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.service.ChannelService;

public class FileChannelService implements ChannelService {

	@Override
	public Channel create(ChannelType channelType, String name, String description) {
		return null;
	}

	@Override
	public Channel read(UUID id) {
		return null;
	}

	@Override
	public List<Channel> readAll() {
		return List.of();
	}

	@Override
	public void delete(UUID id) {

	}

	@Override
	public void update(UUID id, ChannelType channelType, String newChannelName, String newDescription) {

	}

	@Override
	public boolean isEmpty(UUID id) {
		return false;
	}

	@Override
	public void deleteAll() {

	}
}
