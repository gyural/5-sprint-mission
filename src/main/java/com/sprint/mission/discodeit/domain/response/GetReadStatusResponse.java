package com.sprint.mission.discodeit.domain.response;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class GetReadStatusResponse {
	private final UUID id;
	private final Instant createdAt;
	private final Instant updatedAt;
	private final UUID userId;
	private final UUID channelId;
	private final Instant lastReadAt;

}
