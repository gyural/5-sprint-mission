package com.sprint.mission.discodeit.domain.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UserUpdateResult {
	private UUID id;
	private Instant createdAt;
	private Instant updatedAt;
	private String username;
	private String email;
	private UUID profileId;
}
