package com.sprint.mission.discodeit.repository.jcf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

public class JCFChannelRepository implements ChannelRepository {

	public final Map<UUID, Channel> data;

	public JCFChannelRepository() {
		this.data = new HashMap<>();
	}

	@Override
	public void create(String name, String description) {
		Channel newChannel = new Channel(name, description);
		data.put(newChannel.getId(), newChannel);
	}

	@Override
	public Channel find(UUID id) {
		return Optional.ofNullable(data.get(id))
		  .orElseThrow(() -> new IllegalArgumentException("Channel with ID " + id + " not found"));
	}

	@Override
	public List<Channel> findAll() {
		return data.values().stream().toList();
	}

	@Override
	public void delete(UUID id) {
		if (!data.containsKey(id)) {
			throw new IllegalArgumentException("Channel with ID " + id + " not found");
		}
		data.remove(id);
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
	}
}
