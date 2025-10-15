package com.sprint.mission.discodeit.domain.response;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UpdateUserStatusByUserIdResponse {
	private final UUID id;
	private final Instant createdAt;
	private final Instant updatedAt;
	private final UUID userId;
	private final Instant lastActiveAt;
	private final boolean isOnline;
}
