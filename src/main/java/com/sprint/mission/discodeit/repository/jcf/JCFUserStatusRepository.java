package com.sprint.mission.discodeit.repository.jcf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.sprint.mission.discodeit.domain.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;

@Repository
@ConditionalOnProperty(
  prefix = "discodeit.repository",
  name = "type",
  havingValue = "jcf",
  matchIfMissing = true // 값이 없으면 JCF로 등록
)
public class JCFUserStatusRepository implements UserStatusRepository {

	private final Map<UUID, UserStatus> data;

	/**
	 * Constructs a new in-memory user status repository with an empty data store.
	 */
	public JCFUserStatusRepository() {
		this.data = new HashMap<>();
	}

	/**
	 * Saves or updates the given UserStatus in the repository.
	 *
	 * @param userStatus the UserStatus entity to be saved or updated
	 * @return the saved UserStatus entity
	 */
	@Override
	public UserStatus save(UserStatus userStatus) {
		data.put(userStatus.getId(), userStatus);
		return userStatus;
	}

	/**
	 * Retrieves a UserStatus entity by its unique ID.
	 *
	 * @param id the unique identifier of the UserStatus to retrieve
	 * @return an Optional containing the UserStatus if found, or empty if not present
	 */
	@Override
	public Optional<UserStatus> find(UUID id) {
		return Optional.ofNullable(data.get(id));
	}

	/**
	 * Retrieves the first UserStatus associated with the specified user ID.
	 *
	 * @param userId the unique identifier of the user
	 * @return an Optional containing the matching UserStatus, or empty if none found
	 */
	@Override
	public Optional<UserStatus> findByUserId(UUID userId) {
		return data.values().stream()
		  .filter(userStatus -> userStatus.getUserId().equals(userId))
		  .findFirst();
	}

	/**
	 * Retrieves all stored UserStatus entities.
	 *
	 * @return a list containing all UserStatus objects in the repository
	 */
	@Override
	public List<UserStatus> findAll() {
		return data.values().stream().toList();
	}

	/**
	 * Removes the UserStatus entity with the specified ID from the repository.
	 *
	 * @param id the unique identifier of the UserStatus to remove
	 * @throws IllegalArgumentException if no UserStatus with the given ID exists in the repository
	 */
	@Override
	public void delete(UUID id) {
		if (!data.containsKey(id)) {
			throw new IllegalArgumentException("UserStatus with ID " + id + " not found");
		}
		data.remove(id);
	}

	/**
	 * Checks whether the repository contains a non-null UserStatus for the specified ID.
	 *
	 * @param id the unique identifier to check for presence in the repository
	 * @return true if the ID is not present or its associated value is null; false otherwise
	 */
	@Override
	public boolean isEmpty(UUID id) {
		return !data.containsKey(id) || data.get(id) == null;
	}

	/**
	 * Removes all UserStatus entries from the repository.
	 */
	@Override
	public void deleteAll() {
		data.clear();
	}

	/**
	 * Removes all UserStatus entries associated with the specified user ID from the repository.
	 *
	 * @param userId the user ID whose UserStatus entries should be deleted
	 */
	@Override
	public void deleteByUserId(UUID userId) {
		data.values().removeIf(userStatus -> userStatus.getUserId().equals(userId));
	}
}
