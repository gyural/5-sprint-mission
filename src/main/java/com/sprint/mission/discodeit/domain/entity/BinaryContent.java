package com.sprint.mission.discodeit.domain.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class BinaryContent implements Serializable {
	private static final long serialVersionUID = 1L;

	private final UUID id;
	private final String fileName;
	private final String contentType;
	private final long size;
	private final byte[] content;

	private final Instant createdAt;

	public BinaryContent(byte[] content, long size, String contentType, String fileName) {
		this.id = UUID.randomUUID();
		this.createdAt = Instant.now();
		this.content = content;
		this.size = size;
		this.contentType = contentType;
		this.fileName = fileName;
	}

}
