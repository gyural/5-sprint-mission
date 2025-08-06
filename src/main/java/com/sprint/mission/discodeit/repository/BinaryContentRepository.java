package com.sprint.mission.discodeit.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.entity.BinaryContent;

public interface BinaryContentRepository {
	/**
 * Persists the given BinaryContent entity and returns the saved instance.
 *
 * @param binaryContent the BinaryContent entity to be saved
 * @return the saved BinaryContent entity
 */
public BinaryContent save(BinaryContent binaryContent);

	/**
 * Persists a list of BinaryContent entities and returns the saved instances.
 *
 * @param binaryContents the list of BinaryContent entities to be saved
 * @return a list of the saved BinaryContent entities
 */
public List<BinaryContent> saveAll(List<BinaryContent> binaryContents);

	/**
 * Retrieves the BinaryContent entity with the specified UUID.
 *
 * @param id the UUID of the BinaryContent to retrieve
 * @return an Optional containing the found BinaryContent, or empty if not found
 */
public Optional<BinaryContent> find(UUID id);

	/**
 * Retrieves all BinaryContent entities from the repository.
 *
 * @return a list of all BinaryContent entities
 */
public List<BinaryContent> findAll();

	/**
 * Retrieves all BinaryContent entities whose UUIDs are contained in the specified list.
 *
 * @param ids the list of UUIDs to search for
 * @return a list of BinaryContent entities matching the provided UUIDs
 */
public List<BinaryContent> findAllByIdIn(List<UUID> ids);

	/**
 * Deletes the BinaryContent entity identified by the specified UUID.
 *
 * @param id the UUID of the BinaryContent entity to delete
 */
public void delete(UUID id);

	/**
 * Determines whether the BinaryContent entity identified by the given UUID is empty.
 *
 * @param id the UUID of the BinaryContent entity to check
 * @return true if the entity is empty, false otherwise
 */
boolean isEmpty(UUID id);

	/**
 * Deletes all BinaryContent entities from the repository.
 */
void deleteAll();
}
