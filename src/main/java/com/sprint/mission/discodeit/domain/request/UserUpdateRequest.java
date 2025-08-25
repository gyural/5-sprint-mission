package com.sprint.mission.discodeit.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UserUpdateRequest {

	private final String newUsername;
	private final String newEmail;
	private final String newPassword;
}
