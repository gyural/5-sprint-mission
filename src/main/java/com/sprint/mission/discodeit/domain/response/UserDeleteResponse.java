package com.sprint.mission.discodeit.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UserDeleteResponse {
	private final boolean isDeleted;
	private final String username;

}
