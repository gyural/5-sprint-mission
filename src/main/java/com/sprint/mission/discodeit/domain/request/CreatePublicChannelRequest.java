package com.sprint.mission.discodeit.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class CreatePublicChannelRequest {
	@NotNull
	@NotBlank
	private final String name;
	@NotNull
	@NotBlank
	private final String description;
}
