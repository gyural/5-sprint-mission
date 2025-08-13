package com.sprint.mission.discodeit.domain.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UpdatePublicChannelRequest {
	@NotNull
	private final UUID id;
	private final String name;
	private final String description;
}
