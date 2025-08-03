package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;

public interface ChannelService {
	public void create(String name);

	public Channel read(Long id);

	public void delete(Long id);

	public void update(Long id, String newChannelName);

}
