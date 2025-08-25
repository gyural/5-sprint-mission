package com.sprint.mission.discodeit.domain.request;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CreateReadStatusRequest {
	@NotNull
	private final UUID userId;
	@NotNull
	private final UUID channelId;
	@NotNull
	private final Instant lastReadAt;
}
