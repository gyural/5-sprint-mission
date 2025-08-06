package com.sprint.mission.discodeit.service.basic;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sprint.mission.discodeit.domain.dto.CreateBiContentDTO;
import com.sprint.mission.discodeit.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BinaryContentService {

	private final BinaryContentRepository binaryContentRepository;

	/**
	 * Creates and persists a new BinaryContent entity from the provided DTO.
	 *
	 * @param dto the data transfer object containing information for the new BinaryContent
	 * @return the saved BinaryContent entity
	 */
	public BinaryContent create(CreateBiContentDTO dto) {
		return binaryContentRepository.save(dto.toBinaryContent());
	}

	/**
	 * Retrieves a {@link BinaryContent} entity by its UUID.
	 *
	 * @param id the UUID of the binary content to retrieve
	 * @return the found {@link BinaryContent} entity
	 * @throws IllegalArgumentException if no entity exists for the given ID
	 */
	public BinaryContent find(UUID id) {
		return binaryContentRepository.find(id)
		  .orElseThrow(() -> new IllegalArgumentException("Binary content not found for ID: " + id));
	}

	/**
	 * Retrieves all BinaryContent entities whose IDs are included in the provided list.
	 *
	 * @param ids the list of UUIDs to search for
	 * @return a list of BinaryContent entities matching the given IDs
	 */
	public List<BinaryContent> findAllByIdIn(List<UUID> ids) {
		return binaryContentRepository.findAllByIdIn(ids);
	}

	/**
	 * Deletes the BinaryContent entity with the specified UUID.
	 *
	 * @param id the UUID of the BinaryContent to delete
	 * @throws IllegalArgumentException if no BinaryContent exists for the given ID
	 */
	public void delete(UUID id) {
		if (binaryContentRepository.isEmpty(id)) {
			throw new IllegalArgumentException("Binary content not found for ID: " + id);
		}
		binaryContentRepository.delete(id);
	}

}
