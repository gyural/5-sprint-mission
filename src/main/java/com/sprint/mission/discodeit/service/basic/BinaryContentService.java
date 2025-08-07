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

	public BinaryContent create(CreateBiContentDTO dto) {
		return binaryContentRepository.save(dto.toBinaryContent());
	}

	public BinaryContent find(UUID id) {
		return binaryContentRepository.find(id)
		  .orElseThrow(() -> new IllegalArgumentException("Binary content not found for ID: " + id));
	}

	public List<BinaryContent> findAllByIdIn(List<UUID> ids) {
		return binaryContentRepository.findAllByIdIn(ids);
	}

	public void delete(UUID id) {
		if (binaryContentRepository.isEmpty(id)) {
			throw new IllegalArgumentException("Binary content not found for ID: " + id);
		}
		binaryContentRepository.delete(id);
	}

}
