package com.sprint.mission.discodeit.domain.response;

import java.time.Instant;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.entity.BinaryContent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UserUpdateResponse {
	private final UUID id;
	private final Instant createdAt;
	private final Instant updatedAt;
	private final String username;
	private final String email;
	private final BinaryContent profilePicture;
}
