package com.sprint.mission.discodeit.repository;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.enums.ContentType;

public interface BinaryContentRepository {
	public BinaryContentRepository create(byte[] content, Long size, ContentType contentType, String filename);

	public BinaryContentRepository find(UUID id);

	public List<BinaryContentRepository> findAll();

	public void delete(UUID id);

	boolean isEmpty(UUID id);

	void deleteAll();
}
