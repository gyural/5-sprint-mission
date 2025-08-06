package com.sprint.mission.discodeit.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.entity.Channel;

public interface ChannelRepository {
	/**
 * Persists the given Channel entity and returns the saved instance.
 *
 * @param channel the Channel entity to be saved
 * @return the saved Channel entity
 */
public Channel save(Channel channel);

	public Optional<Channel> find(UUID id);

	public List<Channel> findAll();

	public void delete(UUID id);

	boolean isEmpty(UUID id);

	void deleteAll();

	Long count();
}
