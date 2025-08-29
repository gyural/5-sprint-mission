package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.CreateBiContentDTO;
import com.sprint.mission.discodeit.domain.dto.FindBiContentResult;
import com.sprint.mission.discodeit.domain.dto.FindBiContentsIdInDTO;
import com.sprint.mission.discodeit.domain.entity.BinaryContent;

public interface BinaryContentService {

	public BinaryContent create(CreateBiContentDTO dto);

	public FindBiContentResult find(UUID id);

	public List<FindBiContentResult> findAllByIdIn(FindBiContentsIdInDTO dto);

	public void delete(UUID id);
}
