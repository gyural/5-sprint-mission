package com.sprint.mission.discodeit.domain.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class FindBiContentResult {
	private final UUID id;
	private final Instant createdAt;
	private final String fileName;
	private final String contentType;
	private final byte[] bytes;
	private final long size;
}
