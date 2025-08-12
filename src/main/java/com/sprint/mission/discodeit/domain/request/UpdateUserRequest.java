package com.sprint.mission.discodeit.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UpdateUserRequest {

	private final String username;
	private final String email;
	private final String password;
}
