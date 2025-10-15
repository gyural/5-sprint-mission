package com.sprint.mission.discodeit.domain.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UpdateReadStatusDTO {
	private final UUID id;
	private final Instant newLastReadAt;

}
