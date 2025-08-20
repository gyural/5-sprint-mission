package com.sprint.mission.discodeit.service.basic;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sprint.mission.discodeit.domain.dto.CreateBiContentDTO;
import com.sprint.mission.discodeit.domain.dto.FindBiContentIdInDTO;
import com.sprint.mission.discodeit.domain.dto.FindBiContentsIdInDTO;
import com.sprint.mission.discodeit.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasicBinaryContentService implements BinaryContentService {

	private final BinaryContentRepository binaryContentRepository;

	@Override
	public BinaryContent create(CreateBiContentDTO dto) {
		return binaryContentRepository.save(DTOtoBinaryContent(dto));
	}

	@Override
	public BinaryContent find(FindBiContentIdInDTO dto) {
		UUID id = dto.getId();
		return binaryContentRepository.find(id)
		  .orElseThrow(() -> new NoSuchElementException("Binary content not found for ID: " + id));
	}

	@Override
	public List<BinaryContent> findAllByIdIn(FindBiContentsIdInDTO dto) {
		return binaryContentRepository.findAllByIdIn(dto.getIds());
	}

	@Override
	public void delete(UUID id) {
		if (binaryContentRepository.isEmpty(id)) {
			throw new IllegalArgumentException("Binary content not found for ID: " + id);
		}
		binaryContentRepository.delete(id);
	}

	private BinaryContent DTOtoBinaryContent(CreateBiContentDTO dto) {
		byte[] content = dto.getContent();
		long size = dto.getSize();
		String contentType = dto.getContentType();
		String filename = dto.getFileName();
		return new BinaryContent(content, size, contentType, filename);
	}

}
