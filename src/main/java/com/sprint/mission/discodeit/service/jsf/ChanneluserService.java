package com.sprint.mission.discodeit.service.jsf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.ChannelUser;

public class ChanneluserService {

	Map<UUID, ChannelUser> data;

	public ChanneluserService() {
		this.data = new HashMap<>();
	}

	public List<ChannelUser> getChannelUsersByChannel(UUID channelId) {
		return data.values().stream()
		  .filter(channelUser -> channelUser.getChannelId().equals(channelId))
		  .toList();
	}
}
