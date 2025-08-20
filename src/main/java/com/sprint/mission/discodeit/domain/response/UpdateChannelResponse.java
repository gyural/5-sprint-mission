package com.sprint.mission.discodeit.domain.response;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UpdateChannelResponse {

	private UUID id;
	private Instant createdAt;
	private Instant updatedAt;
	private String type;
	private String name;
	private String description;
}
