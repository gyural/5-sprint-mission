package com.sprint.mission.discodeit.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.entity.UserStatus;

public interface UserStatusRepository {

	/**
 * Persists the given UserStatus entity.
 *
 * @param userStatus the UserStatus entity to be saved
 * @return the persisted UserStatus entity
 */
public UserStatus save(UserStatus userStatus);

	/**
 * Retrieves a UserStatus entity by its unique identifier.
 *
 * @param id the unique identifier of the UserStatus entity
 * @return an Optional containing the UserStatus if found, or an empty Optional if not found
 */
public Optional<UserStatus> find(UUID id);

	/**
 * Retrieves a UserStatus entity associated with the specified user ID.
 *
 * @param userId the unique identifier of the user
 * @return an Optional containing the UserStatus if found, or an empty Optional if not found
 */
public Optional<UserStatus> findByUserId(UUID userId);

	/**
 * Retrieves all UserStatus entities.
 *
 * @return a list of all UserStatus entities
 */
public List<UserStatus> findAll();

	/**
 * Deletes the UserStatus entity with the specified unique identifier.
 *
 * @param id the unique identifier of the UserStatus to delete
 */
public void delete(UUID id);

	/**
 * Determines whether the `UserStatus` entity identified by the given UUID is empty.
 *
 * @param id the unique identifier of the `UserStatus` entity to check
 * @return `true` if the `UserStatus` entity is empty; `false` otherwise
 */
boolean isEmpty(UUID id);

	/**
 * Deletes all UserStatus entities from the repository.
 */
void deleteAll();

	/**
 * Deletes the UserStatus entity associated with the specified user ID.
 *
 * @param userId the unique identifier of the user whose UserStatus should be deleted
 */
void deleteByUserId(UUID userId);

}
