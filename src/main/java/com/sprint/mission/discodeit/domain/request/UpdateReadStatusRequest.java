package com.sprint.mission.discodeit.domain.request;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UpdateReadStatusRequest {
	private final Instant newLastReadAt;
}
