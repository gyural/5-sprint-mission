package com.sprint.mission.discodeit.repository;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.entity.BinaryContent;

public interface BinaryContentRepository {
	public BinaryContent create(BinaryContent binaryContent);

	public BinaryContent find(UUID id);

	public List<BinaryContent> findAll();

	public void delete(UUID id);

	boolean isEmpty(UUID id);

	void deleteAll();
}
