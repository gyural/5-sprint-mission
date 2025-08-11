package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.CreateReadStatusDTO;
import com.sprint.mission.discodeit.domain.dto.UpdateReadStatusDTO;
import com.sprint.mission.discodeit.domain.entity.ReadStatus;

public interface ReadStatusService {
	public ReadStatus create(CreateReadStatusDTO dto);

	public List<ReadStatus> findAllByUserId(UUID userId);

	public void update(UpdateReadStatusDTO dto);

	public void delete(UUID id);
}
