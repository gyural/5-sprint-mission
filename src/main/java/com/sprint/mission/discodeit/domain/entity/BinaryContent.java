package com.sprint.mission.discodeit.domain.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.enums.ContentType;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class BinaryContent implements Serializable {
	private static final long serialVersionUID = 1L;

	private final UUID id;
	private final String filename;
	private final ContentType contentType;
	private final Integer size;
	private final byte[] content;

	private final Instant createdAt;

	/****
	 * Constructs a new BinaryContent instance with the specified binary data, size, content type, and filename.
	 *
	 * @param content     the binary data to store
	 * @param size        the size of the content in bytes
	 * @param contentType the type of the content
	 * @param filename    the name of the file associated with the content
	 */
	public BinaryContent(byte[] content, Integer size, ContentType contentType, String filename) {
		this.id = UUID.randomUUID();
		this.createdAt = Instant.now();
		this.content = content;
		this.size = size;
		this.contentType = contentType;
		this.filename = filename;
	}
}
