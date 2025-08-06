package com.sprint.mission.discodeit.repository.jcf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.sprint.mission.discodeit.domain.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;

@Repository
@ConditionalOnProperty(
  prefix = "discodeit.repository",
  name = "type",
  havingValue = "jcf",
  matchIfMissing = true // 값이 없으면 JCF로 등록
)
public class JCFReadStatusRepository implements ReadStatusRepository {

	public final Map<UUID, ReadStatus> data;

	/**
	 * Constructs a new in-memory repository for ReadStatus entities using a HashMap for storage.
	 */
	public JCFReadStatusRepository() {
		this.data = new HashMap<>();
	}

	/**
	 * Saves or updates the given ReadStatus in the repository.
	 *
	 * @param readStatus the ReadStatus entity to be saved or updated
	 * @return the saved ReadStatus entity
	 */
	@Override
	public ReadStatus save(ReadStatus readStatus) {
		data.put(readStatus.getId(), readStatus);
		return readStatus;
	}

	/**
	 * Retrieves a ReadStatus entity by its unique identifier.
	 *
	 * @param id the UUID of the ReadStatus to retrieve
	 * @return an Optional containing the ReadStatus if found, or empty if not present
	 */
	@Override
	public Optional<ReadStatus> find(UUID id) {
		return Optional.ofNullable(data.get(id));
	}

	/**
	 * Retrieves the first {@code ReadStatus} matching the specified user ID and channel ID.
	 *
	 * @param userId the UUID of the user
	 * @param channelId the UUID of the channel
	 * @return an {@code Optional} containing the matching {@code ReadStatus}, or empty if none found
	 */
	@Override
	public Optional<ReadStatus> findByUserIdAndChannelId(UUID userId, UUID channelId) {
		return data.values().stream()
		  .filter(readStatus -> readStatus.getUserId().equals(userId) && readStatus.getChannelId().equals(channelId))
		  .findFirst();
	}

	/**
	 * Retrieves all ReadStatus entities associated with the specified user ID.
	 *
	 * @param userId the UUID of the user whose ReadStatus entries are to be retrieved
	 * @return a list of ReadStatus entities for the given user ID; empty if none found
	 */
	@Override
	public List<ReadStatus> findAllByUserId(UUID userId) {
		return data.values().stream()
		  .filter(readStatus -> readStatus.getUserId().equals(userId))
		  .toList();
	}

	/**
	 * Retrieves all ReadStatus entities associated with the specified channel ID.
	 *
	 * @param channelId the ID of the channel to filter ReadStatus entities by
	 * @return a list of ReadStatus entities for the given channel ID
	 */
	@Override
	public List<ReadStatus> findAllByChannelId(UUID channelId) {
		return data.values().stream()
		  .filter(readStatus -> readStatus.getChannelId().equals(channelId))
		  .toList();
	}

	/**
	 * Retrieves all stored ReadStatus entities.
	 *
	 * @return a list containing all ReadStatus objects in the repository
	 */
	@Override
	public List<ReadStatus> findAll() {
		return data.values().stream().toList();
	}

	/**
	 * Removes the ReadStatus entity with the specified ID from the repository.
	 *
	 * @param id the unique identifier of the ReadStatus to remove
	 * @throws IllegalArgumentException if no ReadStatus with the given ID exists
	 */
	@Override
	public void delete(UUID id) {
		if (!data.containsKey(id)) {
			throw new IllegalArgumentException("ReadStatus with ID " + id + " not found");
		}
		data.remove(id);
	}

	/**
	 * Checks whether the repository contains a non-null ReadStatus for the specified ID.
	 *
	 * @param id the UUID of the ReadStatus to check
	 * @return true if no entry exists for the given ID or the entry is null; false otherwise
	 */
	@Override
	public boolean isEmpty(UUID id) {
		return !data.containsKey(id) || data.get(id) == null;
	}

	/**
	 * Removes all ReadStatus entries associated with the specified channel ID from the repository.
	 *
	 * @param channelId the ID of the channel whose ReadStatus entries should be deleted
	 */
	@Override
	public void deleteByChannelId(UUID channelId) {
		data.values().removeIf(readStatus -> readStatus.getChannelId().equals(channelId));
	}

	/**
	 * Removes all ReadStatus entries from the repository.
	 */
	@Override
	public void deleteAll() {
		data.clear();
	}
}
