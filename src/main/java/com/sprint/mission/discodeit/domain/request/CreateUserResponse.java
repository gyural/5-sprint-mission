package com.sprint.mission.discodeit.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CreateUserResponse {
	private final String message;
	private final String username;
}
