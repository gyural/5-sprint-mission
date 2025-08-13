package com.sprint.mission.discodeit.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ErrorResponse {
	private final int status;
	private final String errMessage;
}
