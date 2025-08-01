package com.sprint.mission.discodeit.repository.jcf;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.repository.BinaryContentRepository;

public interface ReadStatusRepository {
	public BinaryContentRepository create();

	public BinaryContentRepository find(UUID id);

	public List<BinaryContentRepository> findAll();

	public void delete(UUID id);

	boolean isEmpty(UUID id);

	void deleteAll();
}
