package com.sprint.mission.discodeit.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UpdateMessageRequest {

	@NotNull
	@NotBlank
	private final String newContent;
}
