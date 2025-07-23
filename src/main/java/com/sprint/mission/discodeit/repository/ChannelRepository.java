package com.sprint.mission.discodeit.repository;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;

public interface ChannelRepository {
	public Channel create(ChannelType channelType, String name, String description);

	public Channel find(UUID id);

	public List<Channel> findAll();

	public void delete(UUID id);

	public void update(UUID id, ChannelType channelType, String newChannelName, String newDescription);

	boolean isEmpty(UUID id);

	void deleteAll();
}
