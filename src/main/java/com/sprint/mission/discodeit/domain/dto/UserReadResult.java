package com.sprint.mission.discodeit.domain.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UserReadResult {
	private final UUID id;
	private final Instant createdAt;
	private final Instant updatedAt;
	private final String username;
	private final String email;
	private final UUID profileId;
	private final boolean online;
}
