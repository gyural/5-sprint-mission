package com.sprint.mission.discodeit.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Channel;

public interface ChannelRepository {
	public Channel save(Channel channel);

	public Optional<Channel> find(UUID id);

	public List<Channel> findAll();

	public void delete(UUID id);

	boolean isEmpty(UUID id);

	void deleteAll();

	Long count();
}
