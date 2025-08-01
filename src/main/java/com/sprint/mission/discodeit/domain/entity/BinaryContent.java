package com.sprint.mission.discodeit.domain.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.enums.ContentType;

import lombok.Getter;

@Getter
public class BinaryContent implements Serializable {
	private static final long serialVersionUID = 1L;

	private UUID id;
	private final String filename;
	private final ContentType contentType;
	private final Long size;
	private final byte[] content;

	private final Instant createdAt;

	public BinaryContent(byte[] content, Long size, ContentType contentType, String filename) {
		this.id = UUID.randomUUID();
		this.createdAt = Instant.now();
		this.content = content;
		this.size = size;
		this.contentType = contentType;
		this.filename = filename;
	}
}
