package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.CreateBiContentDTO;
import com.sprint.mission.discodeit.domain.entity.BinaryContent;

public interface BinaryContentService {

	public BinaryContent create(CreateBiContentDTO dto);

	public BinaryContent find(UUID id);

	public List<BinaryContent> findAllByIdIn(List<UUID> ids);

	public void delete(UUID id);
}
