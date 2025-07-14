package com.sprint.mission.discodeit.service.jsf;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

public class JCFChannelService implements ChannelService {
	private static final Map<UUID, Channel> data = new HashMap<>();
	private final JCFUserService userService = new JCFUserService();
	private final JCFMessageService messageService = new JCFMessageService();

	@Override
	public void create(String name, String description) {
		Channel newChannel = new Channel(name, description);
		data.put(newChannel.getId(), newChannel);
	}

	@Override
	public Channel read(UUID id) {
		return Optional.ofNullable(data.get(id))
		  .orElseThrow(() -> new IllegalArgumentException("Channel with ID " + id + " not found"));
	}

	@Override
	public void delete(UUID id) {
		if (!data.containsKey(id)) {
			throw new IllegalArgumentException("Channel with ID " + id + " not found");
		}
		data.remove(id);

		// 연관된 메시지도 삭제
		messageService.readAllByChannelId(id)
		  .forEach(message -> messageService.delete(message.getId()));
	}

	@Override
	public void update(UUID id, String newChannelName, String newDescription) {
		if (!data.containsKey(id)) {
			throw new IllegalArgumentException("Channel with ID " + id + " not found");
		}

		data.get(id).setUpdatedAt(System.currentTimeMillis());

		data.get(id).setName(newChannelName);
		data.get(id).setDescription(newDescription);
	}

	@Override
	public boolean isEmpty(UUID id) {
		return data.get(id) == null;
	}
}
