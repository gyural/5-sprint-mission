package com.sprint.mission.discodeit.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UserLoginInfoDTO {
	private final String username;
	private final String email;
}
