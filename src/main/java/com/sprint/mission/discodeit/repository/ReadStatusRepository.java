package com.sprint.mission.discodeit.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.entity.ReadStatus;

public interface ReadStatusRepository {
	/**
 * Persists the given ReadStatus entity and returns the saved instance.
 *
 * @param readStatus the ReadStatus entity to be saved
 * @return the persisted ReadStatus entity
 */
public ReadStatus save(ReadStatus readStatus);

	/**
 * Retrieves a ReadStatus entity by its unique identifier.
 *
 * @param id the unique identifier of the ReadStatus entity
 * @return an Optional containing the ReadStatus if found, or empty if not present
 */
public Optional<ReadStatus> find(UUID id);

	/**
 * Retrieves a {@code ReadStatus} entity matching the specified user ID and channel ID.
 *
 * @param userId the unique identifier of the user
 * @param channelId the unique identifier of the channel
 * @return an {@code Optional} containing the matching {@code ReadStatus} if found, or empty if not present
 */
public Optional<ReadStatus> findByUserIdAndChannelId(UUID userId, UUID channelId);

	/**
 * Retrieves all ReadStatus entities associated with the specified user ID.
 *
 * @param userId the unique identifier of the user
 * @return a list of ReadStatus entities for the given user ID
 */
public List<ReadStatus> findAllByUserId(UUID userId);

	/**
 * Retrieves all ReadStatus entities associated with the specified channel ID.
 *
 * @param channelId the unique identifier of the channel
 * @return a list of ReadStatus entities for the given channel ID
 */
public List<ReadStatus> findAllByChannelId(UUID channelId);

	/**
 * Retrieves all ReadStatus entities.
 *
 * @return a list of all ReadStatus entities
 */
public List<ReadStatus> findAll();

	/**
 * Deletes the ReadStatus entity with the specified unique identifier.
 *
 * @param id the unique identifier of the ReadStatus to delete
 */
public void delete(UUID id);

	/**
 * Determines whether a ReadStatus entity with the specified ID exists.
 *
 * @param id the unique identifier of the ReadStatus entity
 * @return true if no ReadStatus entity with the given ID exists; false otherwise
 */
boolean isEmpty(UUID id);

	/**
 * Deletes all ReadStatus entities associated with the specified channel ID.
 *
 * @param channelId the unique identifier of the channel whose read statuses will be deleted
 */
public void deleteByChannelId(UUID channelId);

	/**
 * Deletes all ReadStatus entities from the repository.
 */
void deleteAll();

}
