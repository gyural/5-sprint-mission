package com.sprint.mission.discodeit.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UpdatePublicChannelRequest {
	private final String name;
	private final String description;
}
