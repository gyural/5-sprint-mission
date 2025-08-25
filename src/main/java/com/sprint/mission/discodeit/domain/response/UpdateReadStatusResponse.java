package com.sprint.mission.discodeit.domain.response;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UpdateReadStatusResponse {

	private final UUID id;
	private final Instant createdAt;
	private final Instant updatedAt;
	private final Instant lastReadAt;

	private final UUID userId;
	private final UUID channelId;
}
