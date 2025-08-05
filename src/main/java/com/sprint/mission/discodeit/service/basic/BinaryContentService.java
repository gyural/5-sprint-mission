package com.sprint.mission.discodeit.service.basic;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sprint.mission.discodeit.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BinaryContentService {

	private final BinaryContentRepository binaryContentRepository;

	public BinaryContent create() {
		return null;
	}

	public BinaryContent find() {

		return null;
	}

	public List<BinaryContent> findAllByIdIn(List<UUID> ids) {
		return List.of();
	}

	public void delete(UUID id) {
		if (binaryContentRepository.isEmpty(id)) {
			throw new IllegalArgumentException("Binary content not found for ID: " + id);
		}
		binaryContentRepository.delete(id);
	}

}
