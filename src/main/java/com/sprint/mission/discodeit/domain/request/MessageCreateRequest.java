package com.sprint.mission.discodeit.domain.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class MessageCreateRequest {
	@NotNull
	@NotBlank
	private final String content;
	@NotNull
	private final UUID authorId;
	@NotNull
	private final UUID channelId;
}
