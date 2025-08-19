package com.sprint.mission.discodeit.domain.request;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class CreatePrivateChannelRequest {
	// @NotNull
	// @NotBlank
	// private final String name;
	// @NotNull
	// @NotBlank
	// private final String description;
	@NotNull
	// @Size(min = 1)
	private final List<UUID> userIds;
}
