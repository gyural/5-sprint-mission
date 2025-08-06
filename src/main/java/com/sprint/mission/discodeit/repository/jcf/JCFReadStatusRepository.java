package com.sprint.mission.discodeit.repository.jcf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.sprint.mission.discodeit.domain.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;

@Repository
public class JCFReadStatusRepository implements ReadStatusRepository {

	public final Map<UUID, ReadStatus> data;

	public JCFReadStatusRepository() {
		this.data = new HashMap<>();
	}

	@Override
	public ReadStatus save(ReadStatus readStatus) {
		data.put(readStatus.getId(), readStatus);
		return readStatus;
	}

	@Override
	public Optional<ReadStatus> find(UUID id) {
		return Optional.of(data.get(id));
	}

	@Override
	public Optional<ReadStatus> findByUserIdAndChannelId(UUID userId, UUID channelId) {
		return data.values().stream()
		  .filter(readStatus -> readStatus.getUserId().equals(userId) && readStatus.getChannelId().equals(channelId))
		  .findFirst();
	}

	@Override
	public List<ReadStatus> findAllByUserId(UUID userId) {
		return data.values().stream()
		  .filter(readStatus -> readStatus.getUserId().equals(userId))
		  .toList();
	}

	@Override
	public List<ReadStatus> findAllByChannelId(UUID channelId) {
		return data.values().stream()
		  .filter(readStatus -> readStatus.getChannelId().equals(channelId))
		  .toList();
	}

	@Override
	public List<ReadStatus> findAll() {
		return data.values().stream().toList();
	}

	@Override
	public void delete(UUID id) {
		if (!data.containsKey(id)) {
			throw new IllegalArgumentException("ReadStatus with ID " + id + " not found");
		}
		data.remove(id);
	}

	@Override
	public boolean isEmpty(UUID id) {
		return !data.containsKey(id) || data.get(id) == null;
	}

	@Override
	public void deleteByChannelId(UUID channelId) {
		data.values().removeIf(readStatus -> readStatus.getChannelId().equals(channelId));
	}

	@Override
	public void deleteAll() {
		data.clear();
	}
}
