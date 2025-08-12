package com.sprint.mission.discodeit.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UserLoginResponse {
	private final boolean success;
	private final UserLoginInfoDTO user;
}
