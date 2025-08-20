package com.sprint.mission.discodeit.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class LoginParams {
	private final String username;
	private final String password;
}
