package com.sprint.mission.discodeit.repository.jcf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.sprint.mission.discodeit.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;

@Repository
@ConditionalOnProperty(
  prefix = "discodeit.repository",
  name = "type",
  havingValue = "jcf",
  matchIfMissing = true // 값이 없으면 JCF로 등록
)
public class JCFBinaryContentRepository implements BinaryContentRepository {

	public final Map<UUID, BinaryContent> data;

	/**
	 * Constructs a new in-memory binary content repository with an empty data store.
	 */
	public JCFBinaryContentRepository() {
		this.data = new HashMap<>();
	}

	/**
	 * Stores or updates the given BinaryContent in the repository.
	 *
	 * @param binaryContent the BinaryContent entity to be saved or updated
	 * @return the saved BinaryContent entity
	 */
	@Override
	public BinaryContent save(BinaryContent binaryContent) {
		data.put(binaryContent.getId(), binaryContent);
		return binaryContent;
	}

	/**
	 * Saves or updates multiple BinaryContent entities in the repository.
	 *
	 * @param binaryContents the list of BinaryContent entities to be saved or updated
	 * @return the list of saved or updated BinaryContent entities
	 */
	@Override
	public List<BinaryContent> saveAll(List<BinaryContent> binaryContents) {
		binaryContents.forEach(binaryContent -> data.put(binaryContent.getId(), binaryContent));
		return binaryContents;
	}

	/**
	 * Retrieves the BinaryContent associated with the specified ID, if present.
	 *
	 * @param id the UUID of the BinaryContent to retrieve
	 * @return an Optional containing the BinaryContent if found, or Optional.empty() if not found
	 */
	@Override
	public Optional<BinaryContent> find(UUID id) {
		return Optional.ofNullable(data.get(id));
	}

	/**
	 * Retrieves all stored BinaryContent entities.
	 *
	 * @return a list containing all BinaryContent objects in the repository
	 */
	@Override
	public List<BinaryContent> findAll() {
		return data.values().stream().toList();
	}

	/**
	 * Retrieves all BinaryContent entities whose IDs are contained in the specified list.
	 *
	 * @param ids the list of UUIDs to search for
	 * @return a list of BinaryContent entities matching the provided IDs
	 */
	@Override
	public List<BinaryContent> findAllByIdIn(List<UUID> ids) {
		return data.values().stream()
		  .filter(binaryContent -> ids.contains(binaryContent.getId()))
		  .toList();
	}

	/**
	 * Removes the BinaryContent entity with the specified ID from the repository.
	 *
	 * @param id the UUID of the BinaryContent to remove
	 * @throws IllegalArgumentException if no BinaryContent with the given ID exists in the repository
	 */
	@Override
	public void delete(UUID id) {
		if (!data.containsKey(id)) {
			throw new IllegalArgumentException("BinaryContent with ID " + id + " not found");
		}
		data.remove(id);
	}

	/**
	 * Checks whether the repository contains a non-null BinaryContent for the specified ID.
	 *
	 * @param id the UUID of the BinaryContent to check
	 * @return true if the ID is not present or its associated value is null; false otherwise
	 */
	@Override
	public boolean isEmpty(UUID id) {
		return !data.containsKey(id) || data.get(id) == null;
	}

	/**
	 * Removes all BinaryContent entities from the repository.
	 */
	@Override
	public void deleteAll() {
		data.clear();
	}
}
