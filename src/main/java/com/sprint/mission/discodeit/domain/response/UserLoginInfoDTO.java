package com.sprint.mission.discodeit.domain.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UserLoginInfoDTO {
	private final UUID id;
	private final String username;
	private final String email;
}
