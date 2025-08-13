package com.sprint.mission.discodeit.domain.response;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UpdateUserStatusResponse {
	private UUID userId;
	private Instant lastActiveAt;
	private boolean isOnline;
}
