package com.sprint.mission.discodeit.controller;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CreateMessageRequest {
	@NotNull
	@NotBlank
	private final String content;
	@NotNull
	private final UUID authorId;
	@NotNull
	private final UUID channelId;
}
