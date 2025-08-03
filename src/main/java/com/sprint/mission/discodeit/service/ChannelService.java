package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Channel;

public interface ChannelService {
	public void create(String name, String description);

	public Channel read(UUID id);

	public List<Channel> readAll();

	public void delete(UUID id);

	public void update(UUID id, String newChannelName, String newDescription);

	boolean isEmpty(UUID id);

	void deleteAll();

}
