package com.sprint.mission.discodeit.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.entity.BinaryContents;

public interface BinaryContentRepository {
	public BinaryContents save(BinaryContents binaryContents);

	public List<BinaryContents> saveAll(List<BinaryContents> binaryContents);

	public Optional<BinaryContents> find(UUID id);

	public List<BinaryContents> findAll();

	public List<BinaryContents> findAllByIdIn(List<UUID> ids);

	public void delete(UUID id);

	boolean isEmpty(UUID id);

	void deleteAll();
}
