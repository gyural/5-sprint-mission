package com.sprint.mission.discodeit.domain.request;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UpdateUserStatusRequest {
	private Instant newLastActiveAt;
}
