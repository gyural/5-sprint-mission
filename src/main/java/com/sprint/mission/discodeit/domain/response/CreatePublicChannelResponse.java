package com.sprint.mission.discodeit.domain.response;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatePublicChannelResponse {
	private UUID id;
	private Instant createdAt;
	private Instant updatedAt;
	private String type;
	private String name;
	private String description;
}
