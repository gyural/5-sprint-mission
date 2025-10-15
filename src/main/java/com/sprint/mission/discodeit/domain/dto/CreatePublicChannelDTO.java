package com.sprint.mission.discodeit.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class CreatePublicChannelDTO {

	private final String name;
	private final String description;

}
