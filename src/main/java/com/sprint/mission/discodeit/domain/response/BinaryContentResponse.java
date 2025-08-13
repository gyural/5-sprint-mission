package com.sprint.mission.discodeit.domain.response;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class BinaryContentResponse {
	private final UUID id;
	private final Instant createdAt;
	private final String fileName;
	private final String contentType;
	private final byte[] bytes;
	private final long size;
}
