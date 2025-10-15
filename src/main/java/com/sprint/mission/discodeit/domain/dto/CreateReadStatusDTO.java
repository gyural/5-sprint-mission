package com.sprint.mission.discodeit.domain.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class CreateReadStatusDTO {
	private final UUID channelId;
	private final UUID userId;
	private final Instant lastReadAt;
}
