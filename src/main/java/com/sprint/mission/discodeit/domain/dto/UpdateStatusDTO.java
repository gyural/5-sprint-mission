package com.sprint.mission.discodeit.domain.dto;

import java.time.Instant;
import java.util.UUID;

public class UpdateStatusDTO {
	private UUID id;
	private Instant newLastActiveAt;
}
