package com.sprint.mission.discodeit.domain.response;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CreateReadStatusResponse {
	private final UUID id;
	private final Instant createdAt;
	private Instant updatedAt;
	private final UUID userId;
	private final UUID channelId;
	private Instant lastReadAt;
}
