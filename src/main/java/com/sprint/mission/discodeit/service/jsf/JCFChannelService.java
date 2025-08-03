package com.sprint.mission.discodeit.service.jsf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

public class JCFChannelService implements ChannelService {
	public static final Map<UUID, Channel> data = new HashMap<>();
	private final JCFUserService userService;
	private final JCFMessageService messageService;

	public JCFChannelService(JCFUserService userService, JCFMessageService messageService) {
		this.userService = userService;
		this.messageService = messageService;
	}

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
	public List<Channel> readAll() {
		return data.values().stream().toList();
	}

	@Override
	public void delete(UUID id) {
		if (!data.containsKey(id)) {
			throw new IllegalArgumentException("Channel with ID " + id + " not found");
		}
		data.remove(id);

		// 연관된 메시지도 삭제
		messageService.deleteByChannelId(id);
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

	@Override
	public void deleteAll() {
		data.clear();
		userService.deleteAll();
		messageService.deleteAll();
	}
}
